package com.masterwok.coinme.data.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.masterwok.coinme.data.clients.news.NewsApiClient
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.data.repositories.models.from
import java.text.SimpleDateFormat
import java.util.*

class NewsPagingSource constructor(
    private val newsApiClient: NewsApiClient
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val pageIndex = params.key ?: PAGE_INDEX_INITIAL

            val foo = SimpleDateFormat("yyyy-MM-dd")

            val response = newsApiClient.getNews(
                apiKey = API_KEY,
                pageIndex = pageIndex,
                from = foo.format(Date()),
                query = "crypto"
            )



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
        // TODO (JT): Provide key through DI...
        private const val API_KEY = "828a6ecc708a4f969a8f60460c4a6e76"
        private const val PAGE_INDEX_INITIAL = 1
    }
}