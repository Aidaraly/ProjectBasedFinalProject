package kg.iaau.softwarearchitecture.gymtracker.ui.main.recommended

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.iaau.softwarearchitecture.gymtracker.data.Training
import kg.iaau.softwarearchitecture.gymtracker.databinding.ListItemRecommendedBinding
import java.util.*

class RecommendedAdapter(var listener: RecommendedListener): ListAdapter<Training, RecommendedViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        return RecommendedViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
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

class RecommendedViewHolder(private val binding: ListItemRecommendedBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var training: Training

    @SuppressLint("ResourceType")
    fun bind(training: Training) {
        this.training = training
        binding.run {
            tvRecommendedTitle.text = training.subtitle
            sivRecommended.setImageResource(training.image)
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: RecommendedListener): RecommendedViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemRecommendedBinding.inflate(layoutInflater, parent, false)
            return RecommendedViewHolder(binding).apply {
                binding.ivOpen.setOnClickListener {
                    listener.onRecommendedClick(training.id)
                }
            }
        }
    }

}

interface RecommendedListener {
    fun onRecommendedClick(id: UUID)
}