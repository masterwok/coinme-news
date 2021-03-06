package com.masterwok.coinme.data.repositories.models

import android.net.Uri
import android.os.Parcelable
import com.masterwok.coinme.common.extensions.toCalendar
import com.masterwok.coinme.data.clients.news.dtos.ArticleDto
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Article(
    val source: Source,
    val title: String,
    val description: String?,
    val articleUri: Uri,
    val articleImageUri: Uri?,
    val publishedOn: Calendar,
    val content: String
) : Parcelable {
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