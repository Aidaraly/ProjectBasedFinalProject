package kg.iaau.softwarearchitecture.gymtracker.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kg.iaau.softwarearchitecture.gymtracker.R
import kg.iaau.softwarearchitecture.gymtracker.databinding.FragmentTrainingBinding
import kg.iaau.softwarearchitecture.gymtracker.utils.ALL_PAGE
import kg.iaau.softwarearchitecture.gymtracker.utils.MAN_PAGE
import kg.iaau.softwarearchitecture.gymtracker.utils.WOMAN_PAGE

@AndroidEntryPoint
class TrainingFragment : Fragment() {

    private val vm: TrainingViewModel by navGraphViewModels(R.id.gym_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.getTrainingSections()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)
        setupViewPager()
        return binding.root
    }

    private fun setupViewPager() {
        with(binding) {
            viewPagerTraining.adapter = TrainingViewPagerAdapter(this@TrainingFragment)
            TabLayoutMediator(tabLayoutTraining, viewPagerTraining) { tab, position ->
                tab.text = getTitle(position)
            }.attach()
        }
    }

    private fun getTitle(position: Int): String? {
        return when(position){
            ALL_PAGE -> getString(R.string.for_all)
            MAN_PAGE -> getString(R.string.for_man)
            WOMAN_PAGE -> getString(R.string.for_woman)
            else -> null
        }
    }

}