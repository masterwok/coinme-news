package com.masterwok.coinme.data.clients.news.dtos

import com.masterwok.coinme.data.clients.news.constants.Status

data class EverythingResponseDto(
    val status: Status,
    val totalResults: Int,
    val articles: List<ArticleDto>
)