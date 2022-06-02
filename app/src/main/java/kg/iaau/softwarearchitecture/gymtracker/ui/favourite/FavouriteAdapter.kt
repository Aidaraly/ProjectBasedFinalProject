package kg.iaau.softwarearchitecture.gymtracker.ui.favourite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.iaau.softwarearchitecture.gymtracker.R
import kg.iaau.softwarearchitecture.gymtracker.data.Training
import kg.iaau.softwarearchitecture.gymtracker.databinding.ListItemTrainingSectionBinding
import java.util.*

class FavouriteAdapter(var listener: FavouriteListener): ListAdapter<Training, FavouriteViewHolder>(DIFF_CALLBACK), Filterable {

    var currentFilterList: List<Training> = currentList
    var copyCurrentList: List<Training> = currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                currentFilterList = if (charSearch.isEmpty()) {
                    copyCurrentList
                } else {
                    val resultList = ArrayList<Training>()
                    for (row in currentList) {
                        if (row.subtitle.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))
                            || row.category.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = currentFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currentFilterList = results?.values as List<Training>
                submitList(currentFilterList)
            }

        }
    }

    fun setList(trainings: List<Training>) {
        copyCurrentList = trainings
        submitList(trainings)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Training> =
            object : DiffUtil.ItemCallback<Training>() {
                override fun areItemsTheSame(oldItem: Training, newItem: Training) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: Training, newItem: Training) =
                    oldItem.id == newItem.id
            }
    }

}

class FavouriteViewHolder(private val binding: ListItemTrainingSectionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var training: Training

    @SuppressLint("ResourceType")
    fun bind(training: Training) {
        this.training = training
        binding.run {
            tvSectionTime.visibility = View.VISIBLE
            tvSectionTime.text = itemView.resources.getString(R.string.training_time, training.time.toString())
            tvSectionTitle.text = training.category
            tvSectionSubtitle.text = training.subtitle
            ivSection.setImageResource(training.image)
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: FavouriteListener): FavouriteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemTrainingSectionBinding.inflate(layoutInflater, parent, false)
            return FavouriteViewHolder(binding).apply {
                binding.clTraining.setOnClickListener {
                    listener.onFavouriteClick(training.id)
                }
            }
        }
    }

}

interface FavouriteListener {
    fun onFavouriteClick(id: UUID)
}