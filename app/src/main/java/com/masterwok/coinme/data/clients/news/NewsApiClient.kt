package com.masterwok.coinme.data.clients.news

import com.masterwok.coinme.data.clients.news.dtos.EverythingResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiClient {

    @GET("portfolio.json")
    suspend fun getNews(): Response<EverythingResponseDto>

}