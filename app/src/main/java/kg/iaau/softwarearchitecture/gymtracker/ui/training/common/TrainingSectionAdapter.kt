package kg.iaau.softwarearchitecture.gymtracker.ui.training.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.iaau.softwarearchitecture.gymtracker.data.TrainingSection
import kg.iaau.softwarearchitecture.gymtracker.databinding.ListItemTrainingSectionBinding

class TrainingSectionAdapter(var listener: TrainingSectionListener): ListAdapter<TrainingSection, TrainingSectionViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingSectionViewHolder {
        return TrainingSectionViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: TrainingSectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TrainingSection> =
            object : DiffUtil.ItemCallback<TrainingSection>() {
                override fun areItemsTheSame(oldItem: TrainingSection, newItem: TrainingSection) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: TrainingSection, newItem: TrainingSection) =
                    oldItem.id == newItem.id
            }
    }

}

class TrainingSectionViewHolder(private val binding: ListItemTrainingSectionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var section: TrainingSection

    @SuppressLint("ResourceType")
    fun bind(section: TrainingSection) {
        this.section = section
        binding.run {
            tvSectionTitle.text = section.title
            tvSectionSubtitle.text = section.subtitle
            ivSection.setImageResource(section.image)
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: TrainingSectionListener): TrainingSectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemTrainingSectionBinding.inflate(layoutInflater, parent, false)
            return TrainingSectionViewHolder(binding).apply {
                binding.clTraining.setOnClickListener {
                    listener.onTrainingSectionClick(section.title, section.gender)
                }
            }
        }
    }

}

interface TrainingSectionListener {
    fun onTrainingSectionClick(category: String, gender: String? = null)
}