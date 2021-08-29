package com.masterwok.coinme.data.repositories.models

import android.os.Parcelable
import com.masterwok.coinme.data.clients.news.dtos.SourceDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String?,
    val name: String
) : Parcelable {
    companion object
}

fun Source.Companion.from(source: SourceDto) = with(source) {
    Source(
        id = id,
        name = name
    )
}
