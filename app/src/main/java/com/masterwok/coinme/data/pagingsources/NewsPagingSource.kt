package com.masterwok.coinme.data.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.masterwok.coinme.common.extensions.toIso8601
import com.masterwok.coinme.data.clients.news.NewsApiClient
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.data.repositories.models.from
import java.util.*

class NewsPagingSource constructor(
    private val newsApiClient: NewsApiClient,
    private val apiKey: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val pageIndex = params.key ?: PAGE_INDEX_INITIAL

            val response = newsApiClient.getNews(
                apiKey = apiKey,
                pageIndex = pageIndex,
                pageSize = params.loadSize,
                from = Date().toIso8601(),
                query = "crypto"
            )

            if (!response.isSuccessful) {
                // TODO (JT): Handle 426 upgrade required error code.

                error("Failed to fetch news.")
            }

            val everythingResponseDto = checkNotNull(response.body())
            val articles = everythingResponseDto.articles.map(Article.Companion::from)

            return LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = if (articles.isEmpty()) null else pageIndex + 1
            )
        } catch (exception: Exception) {
            val x = 1
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
        private const val PAGE_INDEX_INITIAL = 1
    }
}