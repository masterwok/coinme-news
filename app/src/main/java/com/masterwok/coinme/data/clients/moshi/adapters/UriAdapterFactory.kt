package com.masterwok.coinme.data.clients.moshi.adapters

import android.net.Uri
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class UriAdapterFactory : JsonAdapter.Factory {

    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<Uri>? = if (type != Uri::class.java) {
        null
    } else {
        Companion
    }

    private companion object : JsonAdapter<Uri>() {
        override fun fromJson(reader: JsonReader): Uri? = Uri.parse(reader.nextString())

        override fun toJson(writer: JsonWriter, value: Uri?) {
            writer.value(value?.toString())
        }
    }
}