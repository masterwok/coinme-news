package com.masterwok.coinme.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.masterwok.coinme.data.clients.news.NewsApiClient
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.data.pagingsources.NewsPagingSource
import com.masterwok.coinme.data.repositories.contracts.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiClient: NewsApiClient
) : NewsRepository {

    override val articlePagingDataFlow: Flow<PagingData<Article>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        NewsPagingSource(newsApiClient)
    }.flow

}