package com.masterwok.coinme.data.clients.news.dtos

import android.net.Uri
import com.squareup.moshi.Json
import java.util.*

data class ArticleDto(
    val source: SourceDto,
    val author: String?,
    val title: String,
    val description: String?,
    @Json(name = "url") val articleUri: Uri,
    @Json(name = "urlToImage") val articleImageUri: Uri?,
    @Json(name = "publishedAt") val publishedOn: Date,
    val content: String
)


