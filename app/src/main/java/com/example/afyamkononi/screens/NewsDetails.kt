package com.example.afyamkononi.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.ActivityNewsDetailsBinding
import com.example.afyamkononi.news.model.Article
import com.example.afyamkononi.util.formatPublishedAt

class NewsDetails : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val receivedIntent = intent
        val receivedArticle = receivedIntent.getSerializableExtra("article") as? Article

        if (receivedArticle != null) {
            binding.apply {
                Glide.with(this@NewsDetails)
                    .load(receivedArticle.urlToImage)
                    .centerCrop()
                    .placeholder(R.drawable.strange)
                    .error(R.drawable.news)
                    .into(newsDescImage)
                newsDescTitle.text = receivedArticle.title
                newsDescDate.text = receivedArticle.publishedAt?.formatPublishedAt()
                newsDescContent.text = receivedArticle.content
            }
        }
    }
}
