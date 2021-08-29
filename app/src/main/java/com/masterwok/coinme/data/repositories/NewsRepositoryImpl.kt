package com.masterwok.coinme.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.masterwok.coinme.data.clients.news.NewsApiClient
import com.masterwok.coinme.data.pagingsources.NewsPagingSource
import com.masterwok.coinme.data.repositories.contracts.NewsRepository
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.data.repositories.models.NewsFilter
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiClient: NewsApiClient,
    private val newsApiKey: String
) : NewsRepository {

    override fun getNewsPager(newsFilter: NewsFilter): Pager<Int, Article> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        NewsPagingSource(
            newsApiClient,
            newsApiKey,
            newsFilter
        )
    }

}