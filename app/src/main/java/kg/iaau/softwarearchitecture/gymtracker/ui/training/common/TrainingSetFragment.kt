package kg.iaau.softwarearchitecture.gymtracker.ui.training.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.iaau.softwarearchitecture.gymtracker.R
import kg.iaau.softwarearchitecture.gymtracker.databinding.FragmentAllTrainingBinding
import kg.iaau.softwarearchitecture.gymtracker.ui.training.TrainingViewModel
import java.util.*

@AndroidEntryPoint
class TrainingSetFragment : Fragment(), TrainingListener {

    private val vm: TrainingViewModel by navGraphViewModels(R.id.gym_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentAllTrainingBinding
    private var adapter = TrainingSetAdapter(this)
    private val args: TrainingSetFragmentArgs by navArgs()
    private val category: String by lazy { args.category }
    private val gender: String? by lazy { args.gender }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTrainingBinding.inflate(inflater, container, false)
        vm.getTrainings()
        setupFragmentView()
        observeLiveData()
        return binding.root
    }

    private fun setupFragmentView() {
        with(binding) {
            rvTrainingSection.adapter = adapter
            tabLayoutTraining.visibility = View.GONE
            toolbar.setNavigationOnClickListener {
                view?.findNavController()?.navigateUp()
            }
        }
    }

    private fun observeLiveData() {
        vm.trainingsLiveData.observe(viewLifecycleOwner, { trainings ->
            trainings?.let {
                adapter.submitList(it.filter { training -> training.gender == gender && training.category == category })
            }
        })
    }

    override fun onTrainingClick(id: UUID) {
        val args = Bundle().apply {
            putString("id", id.toString())
        }
        view?.findNavController()?.navigate(R.id.trainingDetailFragment, args)
    }

}