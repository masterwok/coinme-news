package com.masterwok.coinme.data.repositories.contracts

import androidx.paging.PagingData
import com.masterwok.coinme.data.repositories.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    val articlePagingDataFlow: Flow<PagingData<Article>>
}