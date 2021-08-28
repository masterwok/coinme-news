package com.masterwok.coinme.data.clients.moshi.adapters

import com.masterwok.coinme.common.utils.mapToEnum
import com.masterwok.coinme.data.clients.news.constants.Status
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class StatusAdapter : JsonAdapter<Status>() {
    override fun fromJson(reader: JsonReader): Status = mapToEnum(
        reader.nextString(),
        Status::value
    )

    override fun toJson(writer: JsonWriter, value: Status?) {
        writer.value(value?.value)
    }
}

