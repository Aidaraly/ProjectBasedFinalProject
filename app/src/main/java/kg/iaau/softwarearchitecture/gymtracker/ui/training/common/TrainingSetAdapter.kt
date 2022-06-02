package kg.iaau.softwarearchitecture.gymtracker.ui.training.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.iaau.softwarearchitecture.gymtracker.R
import kg.iaau.softwarearchitecture.gymtracker.data.Training
import kg.iaau.softwarearchitecture.gymtracker.databinding.ListItemTrainingSectionBinding
import java.util.*

class TrainingSetAdapter(var listener: TrainingListener): ListAdapter<Training, TrainingSetViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingSetViewHolder {
        return TrainingSetViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: TrainingSetViewHolder, position: Int) {
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

class TrainingSetViewHolder(private val binding: ListItemTrainingSectionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var training: Training

    @SuppressLint("ResourceType")
    fun bind(training: Training) {
        this.training = training
        binding.run {
            tvSectionTime.visibility = View.VISIBLE
            tvSectionTime.text = itemView.resources.getString(R.string.training_time, training.time.toString())
            tvSectionTitle.text = training.title
            tvSectionSubtitle.text = training.subtitle
            ivSection.setImageResource(training.image)
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: TrainingListener): TrainingSetViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemTrainingSectionBinding.inflate(layoutInflater, parent, false)
            return TrainingSetViewHolder(binding).apply {
                binding.clTraining.setOnClickListener {
                    listener.onTrainingClick(training.id)
                }
            }
        }
    }

}

interface TrainingListener {
    fun onTrainingClick(id: UUID)
}