package com.masterwok.coinme.data.clients.news

import com.masterwok.coinme.data.clients.news.dtos.EverythingResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.*

interface NewsApiClient {

    @GET("everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("page") pageIndex: Int,
        @Query("pageSize") pageSize: Int,
        @Query("from") from: String,
        @Query("q") query: String?,
    ): Response<EverythingResponseDto>

}