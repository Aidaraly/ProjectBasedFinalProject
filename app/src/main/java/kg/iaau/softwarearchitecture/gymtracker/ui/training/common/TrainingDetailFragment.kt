package kg.iaau.softwarearchitecture.gymtracker.ui.training.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.iaau.softwarearchitecture.gymtracker.R
import kg.iaau.softwarearchitecture.gymtracker.data.Training
import kg.iaau.softwarearchitecture.gymtracker.databinding.FragmentTrainingDetailBinding
import kg.iaau.softwarearchitecture.gymtracker.ui.training.TrainingViewModel
import kg.iaau.softwarearchitecture.gymtracker.utils.startDialog
import kg.iaau.softwarearchitecture.gymtracker.utils.vibratePhone
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

@AndroidEntryPoint
class TrainingDetailFragment : Fragment() {

    private val vm: TrainingViewModel by navGraphViewModels(R.id.gym_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentTrainingDetailBinding
    private lateinit var menuItem: MenuItem
    private val args: TrainingDetailFragmentArgs by navArgs()
    private val id: UUID by lazy { UUID.fromString(args.id) }
    private enum class TimerStatus {
        STARTED,
        STOPPED
    }
    private var timerStatus = TimerStatus.STOPPED
    private lateinit var countDownTimer: CountDownTimer
    private var timeCountInMilliSeconds by Delegates.notNull<Long>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingDetailBinding.inflate(inflater, container, false)
        vm.getTraining(id)
        observeLiveData()
        return binding.root
    }

    private fun observeLiveData() {
        vm.trainingLiveData.observe(viewLifecycleOwner, { training ->
            training?.let {
                setupFragmentView(training)
                updateMenuItemIcon(training.isFavourite)
                timeCountInMilliSeconds = (training.time + 1) * 1000
            }
        })
    }

    private fun setupFragmentView(training: Training?) {
        with(binding) {
            toolbar.title = training?.title
            toolbar.setNavigationOnClickListener {
                it?.findNavController()?.navigateUp()
            }
            training?.image?.let { ivTraining.setImageResource(it) }
            tvTrainingDescription.text = training?.description
            tvTime.text = hmsTimeFormatter(training!!.time * 1000)
            ivStartStop.setOnClickListener { startStop() }
        }
        onOptionsItemSelected(training)
    }

    private fun onOptionsItemSelected(training: Training?) {
        binding.toolbar.setOnMenuItemClickListener {
            training?.let { item -> vm.updateTraining(item) }
            true
        }
    }

    private fun updateMenuItemIcon(isFavourite: Boolean) {
        with(binding) {
            menuItem = toolbar.menu.getItem(0)
            val menuIcon = if (isFavourite) R.drawable.ic_favourite else R.drawable.ic_favourite_border
            menuItem.setIcon(menuIcon)
        }
    }

    private fun startStop() {
        with(binding) {
            if (timerStatus === TimerStatus.STOPPED) {
                setProgressBarValues()
                ivStartStop.setImageResource(R.drawable.ic_pause)
                timerStatus = TimerStatus.STARTED
                startCountDownTimer()
            } else {
                ivStartStop.setImageResource(R.drawable.ic_start)
                timerStatus = TimerStatus.STOPPED
                stopCountDownTimer()
            }
        }
    }
    
    private fun startCountDownTimer() {
        with(binding) {
            countDownTimer = object : CountDownTimer(timeCountInMilliSeconds, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvTime.text = hmsTimeFormatter(millisUntilFinished)
                    pbCircle.progress = (millisUntilFinished / 1000).toInt()

                }

                override fun onFinish() {
                    tvTime.text = hmsTimeFormatter(timeCountInMilliSeconds - 1)
                    setProgressBarValues()
                    ivStartStop.setImageResource(R.drawable.ic_start)
                    timerStatus = TimerStatus.STOPPED
                    vibratePhone()
                    context?.startDialog()
                }
            }.start()
            countDownTimer.start()
        }
    }
    
    private fun stopCountDownTimer() {
        countDownTimer.cancel()
        timeCountInMilliSeconds
    }

    private fun setProgressBarValues() {
        with(binding) {
            pbCircle.max = timeCountInMilliSeconds.toInt() / 1000
            pbCircle.progress = timeCountInMilliSeconds.toInt() / 1000
        }
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    @SuppressLint("DefaultLocale")
    private fun hmsTimeFormatter(milliSeconds: Long): String {
        return String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(milliSeconds),
            TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    milliSeconds
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    milliSeconds
                )
            )
        )
    }

}