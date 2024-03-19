package com.example.afyamkononi.shared.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.RawNewsItemBinding
import com.example.afyamkononi.patients.news.model.Article
import com.example.afyamkononi.patients.util.formatPublishedAt

class NewsAdapter(
    private val list: List<Article>,
    val clickListener: OnNewsClickListener
) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val rawNewsItemBinding: RawNewsItemBinding) :
        RecyclerView.ViewHolder(rawNewsItemBinding.root) {
        fun setData(news: Article, action: OnNewsClickListener) {
            rawNewsItemBinding.apply {
                news.urlToImage?.let {
                    Glide.with(newsImage.context)
                        .load(it)
                        .centerCrop()
                        .placeholder(R.drawable.strange)
                        .error(R.drawable.news)
                        .into(newsImage)
                }
                newsTitle.text = news.title
                newsDate.text = news.publishedAt?.formatPublishedAt()
            }
            rawNewsItemBinding.root.setOnClickListener {
                action.onNewsClick(news, adapterPosition)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            RawNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = list[position]
        holder.setData(news, clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnNewsClickListener {
        fun onNewsClick(news: Article, position: Int)
    }


}