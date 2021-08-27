package com.masterwok.coinme.features.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.masterwok.coinme.data.repositories.contracts.NewsRepository
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    newsRepository: NewsRepository
) : ViewModel() {

    val articlePagingDataFlow = newsRepository
        .articlePagingDataFlow
        .cachedIn(viewModelScope)

}