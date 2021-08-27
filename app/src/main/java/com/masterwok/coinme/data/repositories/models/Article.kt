package com.masterwok.coinme.data.repositories.models

import android.net.Uri
import com.masterwok.coinme.data.clients.news.dtos.ArticleDto
import com.masterwok.coinme.data.clients.news.dtos.SourceDto

data class Source(
    val id: String?,
    val name: String
) {
    companion object {
        fun from(source: SourceDto) = with(source) {
            Source(
                id = id,
                name = name
            )
        }
    }
}

data class Article(
    val source: Source,
    val title: String,
    val url: Uri
    // TODO (JT): Actually flesh out this data class once paging is hooked up...
) {
    companion object
}

fun Article.Companion.from(articleDto: ArticleDto) = with(articleDto) {
    Article(
        source = Source.from(source),
        title = title,
        url = Uri.EMPTY
    )
}