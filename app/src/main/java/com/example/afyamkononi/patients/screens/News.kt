package com.example.afyamkononi.patients.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.shared.adapters.NewsAdapter
import com.example.afyamkononi.databinding.ActivityNewsBinding
import com.example.afyamkononi.patients.news.model.Article
import com.example.afyamkononi.patients.util.Resource
import com.example.afyamkononi.patients.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class News : AppCompatActivity(), NewsAdapter.OnNewsClickListener {
    private lateinit var binding: ActivityNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private var newsAdapter: NewsAdapter = NewsAdapter(emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewsBinding.inflate(layoutInflater)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Observe the healthNews LiveData from the ViewModel
        newsViewModel.healthNews.observe(this, Observer { result ->
            when (result) {
                is Resource.Success -> {
                    val articles = result.data?.articles ?: emptyList<Article>()
                    newsAdapter = NewsAdapter(articles, this)

                    binding.newsRecyclerView.apply {
                        layoutManager = LinearLayoutManager(this@News)
                        setHasFixedSize(true)
                        adapter = newsAdapter
                    }
                    newsAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }

        })
    }

    override fun onNewsClick(news: Article, position: Int) {
        val article = Article(
            news.title,
            news.content,
            news.description,
            news.publishedAt,
            news.source,
            news.title,
            news.url,
            news.urlToImage
        )
        val intent = Intent(this, NewsDetails::class.java)
        intent.putExtra(
            "article",
            article
        )
        startActivity(intent)
    }
}
