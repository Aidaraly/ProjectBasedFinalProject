package kg.iaau.softwarearchitecture.gymtracker.ui.main.news

import android.annotation.SuppressLint
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
import kg.iaau.softwarearchitecture.gymtracker.databinding.FragmentNewsDetailBinding
import kg.iaau.softwarearchitecture.gymtracker.ui.MainViewModel
import java.util.*

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private val vm: MainViewModel by navGraphViewModels(R.id.gym_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentNewsDetailBinding
    private val args: NewsDetailFragmentArgs by navArgs()
    private val id: UUID by lazy { UUID.fromString(args.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        vm.getNews(id)
        observeLiveData()
        return binding.root
    }

    @SuppressLint("ResourceType")
    private fun observeLiveData() {
        vm.newsLiveData.observe(viewLifecycleOwner, { news ->
            news?.let {
                with(binding) {
                    toolbar.title = news.title
                    toolbar.setNavigationOnClickListener {
                        it?.findNavController()?.navigateUp()
                    }
                    ivNews.setImageResource(news.image)
                    tvNewsDescription.text = news.description
                }
            }
        })
    }


}