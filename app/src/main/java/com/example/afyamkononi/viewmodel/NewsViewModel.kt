package com.example.afyamkononi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afyamkononi.news.model.Article
import com.example.afyamkononi.news.model.NewsResult
import com.example.afyamkononi.repository.NewsDataRepository
import com.example.afyamkononi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val repository: NewsDataRepository
) : ViewModel() {
    private val _healthNews: MutableLiveData<Resource<NewsResult>> = MutableLiveData()
    val healthNews: LiveData<Resource<NewsResult>> get() = _healthNews

    init {
        getHealthNews()
    }

    private fun getHealthNews() {
        viewModelScope.launch {
            _healthNews.value = repository.getHealthNews()
        }
    }
}
