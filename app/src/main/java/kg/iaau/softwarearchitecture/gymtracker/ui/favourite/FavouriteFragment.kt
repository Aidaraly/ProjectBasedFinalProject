package kg.iaau.softwarearchitecture.gymtracker.ui.favourite

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.iaau.softwarearchitecture.gymtracker.R
import kg.iaau.softwarearchitecture.gymtracker.databinding.FragmentFavouriteBinding
import kg.iaau.softwarearchitecture.gymtracker.ui.training.TrainingViewModel
import java.util.*
import androidx.appcompat.app.AppCompatActivity

@AndroidEntryPoint
class FavouriteFragment : Fragment(), FavouriteListener {

    private val vm: TrainingViewModel by navGraphViewModels(R.id.gym_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentFavouriteBinding
    private var adapter = FavouriteAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        vm.getTrainings()
        setupFragmentView()
        observeLiveData()
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        searchFavourite(searchView)
    }

    private fun searchFavourite(searchView: SearchView) {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun setupFragmentView() { binding.rvFavouriteTrainingSection.adapter = adapter }

    private fun observeLiveData() {
        vm.trainingsLiveData.observe(viewLifecycleOwner, { trainings ->
            trainings?.let {
                adapter.setList(it.filter { training -> training.isFavourite })
            }
        })
    }

    override fun onFavouriteClick(id: UUID) {
        val args = Bundle().apply {
            putString("id", id.toString())
        }
        view?.findNavController()?.navigate(R.id.trainingDetailFragment, args)
    }

}