package com.masterwok.coinme.data.clients.news.dtos

import android.net.Uri
import com.masterwok.coinme.data.clients.news.constants.Status
import com.squareup.moshi.Json
import java.util.*

data class SourceDto(
    val id: String?,
    val name: String
)

data class ArticleDto(
    val source: SourceDto,
    val author: String?,
    val title: String,
    val description: String,
    @Json(name = "url") val articleUri: Uri,
    @Json(name = "urlToImage") val articleImageUri: Uri?,
    @Json(name = "publishedAt") val publishedOn: Date,
    val content: String
)


data class EverythingResponseDto(
    val status: Status,
    val totalResults: Int,
    val articles: List<ArticleDto>
)