package com.masterwok.coinme.data.repositories.contracts

import androidx.paging.Pager
import androidx.paging.PagingData
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.data.repositories.models.NewsFilter
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsPager(newsFilter: NewsFilter): Pager<Int, Article>
}