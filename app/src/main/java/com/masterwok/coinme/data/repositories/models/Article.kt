package com.masterwok.coinme.data.repositories.models

import android.net.Uri
import com.masterwok.coinme.common.extensions.toCalendar
import com.masterwok.coinme.data.clients.news.dtos.ArticleDto
import com.masterwok.coinme.data.clients.news.dtos.SourceDto
import java.util.*

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
    val description: String,
    val articleUri: Uri,
    val articleImageUri: Uri,
    val publishedOn: Calendar,
    val content: String
) {
    companion object
}

fun Article.Companion.from(articleDto: ArticleDto) = with(articleDto) {
    Article(
        source = Source.from(source),
        title = title,
        description = description,
        articleUri = articleUri,
        articleImageUri = articleImageUri,
        publishedOn = publishedOn.toCalendar(),
        content = content
    )
}