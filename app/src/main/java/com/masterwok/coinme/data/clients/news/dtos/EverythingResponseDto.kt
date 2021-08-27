package com.masterwok.coinme.data.clients.news.dtos

import android.net.Uri
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
    val url: Uri,
    val urlToImage: Uri,
//    val publishedAt: Date,
    val content: String
)


data class EverythingResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)