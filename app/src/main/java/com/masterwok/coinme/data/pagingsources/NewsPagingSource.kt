package com.masterwok.coinme.data.pagingsources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.masterwok.coinme.common.extensions.toIso8601
import com.masterwok.coinme.data.clients.news.NewsApiClient
import com.masterwok.coinme.data.clients.exceptions.TooManyRequestsException
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.data.repositories.models.NewsFilter
import com.masterwok.coinme.data.repositories.models.from
import java.util.*

class NewsPagingSource constructor(
    private val newsApiClient: NewsApiClient,
    private val apiKey: String,
    private val newsFilter: NewsFilter
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val pageIndex = params.key ?: PAGE_INDEX_INITIAL

            val response = newsApiClient.getNews(
                apiKey = apiKey,
                pageIndex = pageIndex,
                pageSize = params.loadSize,
                from = Date().toIso8601(),
                query = newsFilter.query
            )

            if (!response.isSuccessful) {
                when (response.code()) {
                    429 -> throw TooManyRequestsException()
                    else -> error("Fetch news request unsuccessful: ${response.code()}")
                }
            }

            val everythingResponseDto = checkNotNull(response.body())
            val articles = everythingResponseDto.articles.map(Article.Companion::from)

            return LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = if (articles.isEmpty()) null else pageIndex + 1
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to fetch news", exception)
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, Article>
    ): Int? = state.anchorPosition?.let { anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)

        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    private companion object {
        private const val TAG = "NewsPagingSource"
        private const val PAGE_INDEX_INITIAL = 1
    }
}