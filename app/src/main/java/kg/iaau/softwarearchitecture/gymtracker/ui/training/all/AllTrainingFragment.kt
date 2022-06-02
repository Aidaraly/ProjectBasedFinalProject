package kg.iaau.softwarearchitecture.gymtracker.ui.training.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.iaau.softwarearchitecture.gymtracker.R
import kg.iaau.softwarearchitecture.gymtracker.databinding.FragmentAllTrainingBinding
import kg.iaau.softwarearchitecture.gymtracker.ui.training.TrainingViewModel
import kg.iaau.softwarearchitecture.gymtracker.ui.training.common.TrainingSectionAdapter
import kg.iaau.softwarearchitecture.gymtracker.ui.training.common.TrainingSectionListener

@AndroidEntryPoint
class AllTrainingFragment : Fragment(), TrainingSectionListener {

    private val vm: TrainingViewModel by navGraphViewModels(R.id.gym_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentAllTrainingBinding
    private var adapter = TrainingSectionAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTrainingBinding.inflate(inflater, container, false)
        setupFragmentView()
        observeLiveData()
        return binding.root
    }

    private fun setupFragmentView() { binding.rvTrainingSection.adapter = adapter }

    private fun observeLiveData() {
        vm.trainingSectionLiveData.observe(viewLifecycleOwner, { sections ->
            sections?.let {
                adapter.submitList(it.filter { section -> section.gender.isNullOrEmpty() })
            }
        })
    }

    override fun onTrainingSectionClick(category: String, gender: String?) {
        val args = Bundle().apply {
            putString("category", category)
            putString("gender", gender)
        }
        view?.findNavController()?.navigate(R.id.trainingSetFragment, args)
    }

}