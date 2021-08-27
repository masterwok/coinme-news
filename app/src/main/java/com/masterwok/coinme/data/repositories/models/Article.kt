package com.masterwok.coinme.data.repositories.models

import com.masterwok.coinme.data.clients.news.dtos.ArticleDto

data class Article(
    val title: String
    // TODO (JT): Actually flesh out this data class once paging is hooked up...
) {
    companion object
}

fun Article.Companion.from(source: ArticleDto) = with(source) {
    Article(
        title = title
    )
}