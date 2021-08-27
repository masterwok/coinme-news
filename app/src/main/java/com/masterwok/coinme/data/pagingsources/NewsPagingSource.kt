package com.masterwok.coinme.data.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.masterwok.coinme.data.clients.news.NewsApiClient
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.data.repositories.models.from

class NewsPagingSource constructor(
    private val newsApiClient: NewsApiClient
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val pageIndex = params.key ?: PAGE_INDEX_INITIAL
            val response = newsApiClient.getNews()

            if (!response.isSuccessful) {
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