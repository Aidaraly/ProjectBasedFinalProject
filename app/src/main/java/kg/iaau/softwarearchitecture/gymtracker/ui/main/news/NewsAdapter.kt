package kg.iaau.softwarearchitecture.gymtracker.ui.main.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.iaau.softwarearchitecture.gymtracker.data.News
import kg.iaau.softwarearchitecture.gymtracker.databinding.ListItemNewsBinding
import java.util.*

class NewsAdapter(var listener: NewsListener): ListAdapter<News, NewsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<News> =
            object : DiffUtil.ItemCallback<News>() {
                override fun areItemsTheSame(oldItem: News, newItem: News) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: News, newItem: News) =
                    oldItem.id == newItem.id
            }
    }

}

class NewsViewHolder(private val binding: ListItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var news: News

    @SuppressLint("ResourceType")
    fun bind(news: News) {
        this.news = news
        binding.run {
            tvNewsTitle.text = news.title
            tvNewsSubtitle.text = news.subtitle
            ivNews.setImageResource(news.image)
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: NewsListener): NewsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemNewsBinding.inflate(layoutInflater, parent, false)
            return NewsViewHolder(binding).apply {
                binding.llNews.setOnClickListener {
                    listener.onNewsClick(news.id)
                }
            }
        }
    }

}

interface NewsListener {
    fun onNewsClick(id: UUID)
}