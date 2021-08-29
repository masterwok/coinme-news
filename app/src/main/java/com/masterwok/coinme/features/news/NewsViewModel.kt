package com.masterwok.coinme.features.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.masterwok.coinme.data.repositories.contracts.NewsRepository
import com.masterwok.coinme.data.repositories.models.NewsFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModel @Inject constructor(
    newsRepository: NewsRepository
) : ViewModel() {

    private val _filterStateFlow = MutableStateFlow(DEFAULT_NEWS_FILTER)

    val filterStateFlow = _filterStateFlow.asStateFlow()

    val articlePagingDataFlow = _filterStateFlow.flatMapLatest { newsFilter ->
        newsRepository
            .getNewsPager(newsFilter)
            .flow
            .cachedIn(viewModelScope)
    }

    fun searchNews(newsFilter: NewsFilter) {
        _filterStateFlow.value = newsFilter
    }

    private companion object {
        private val DEFAULT_NEWS_FILTER = NewsFilter(query = "crypto")
    }

}