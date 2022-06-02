package kg.iaau.softwarearchitecture.gymtracker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.iaau.softwarearchitecture.gymtracker.R
import kg.iaau.softwarearchitecture.gymtracker.databinding.FragmentMainBinding
import kg.iaau.softwarearchitecture.gymtracker.ui.MainViewModel
import kg.iaau.softwarearchitecture.gymtracker.ui.main.news.NewsAdapter
import kg.iaau.softwarearchitecture.gymtracker.ui.main.news.NewsListener
import kg.iaau.softwarearchitecture.gymtracker.ui.main.recommended.RecommendedAdapter
import kg.iaau.softwarearchitecture.gymtracker.ui.main.recommended.RecommendedListener
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment(), NewsListener, RecommendedListener {

    private val vm: MainViewModel by navGraphViewModels(R.id.gym_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentMainBinding
    private var newsAdapter = NewsAdapter(this)
    private var recommendedAdapter = RecommendedAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        vm.getAllNews()
        vm.getTrainings()
        setupFragmentView()
        observeLiveData()
        return binding.root
    }

    private fun setupFragmentView() {
        with(binding) {
            rvRecommended.adapter = recommendedAdapter
            rvNews.adapter = newsAdapter
        }
    }

    private fun observeLiveData() {
        vm.allNewsLiveData.observe(viewLifecycleOwner, { news ->
            news?.let {
                newsAdapter.submitList(it)
            }
        })
        vm.trainingsLiveData.observe(viewLifecycleOwner, { trainings ->
            trainings?.let {
                recommendedAdapter.submitList(it.filter { training -> training.isRecommended })
            }
        })
    }

    override fun onNewsClick(id: UUID) {
        val args = Bundle()
        args.putString("id", id.toString())
        view?.findNavController()?.navigate(R.id.newsDetailFragment, args)
    }

    override fun onRecommendedClick(id: UUID) {
        val args = Bundle().apply {
            putString("id", id.toString())
        }
        view?.findNavController()?.navigate(R.id.trainingDetailFragment, args)
    }

}