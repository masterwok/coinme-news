package com.masterwok.coinme.data.clients.moshi.adapters

import com.masterwok.coinme.data.clients.news.constants.Status
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import kotlin.reflect.KProperty1

class StatusAdapter : JsonAdapter.Factory {

    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<Status>? = if (type != Status::class.java) {
        null
    } else {
        Companion
    }

    private companion object : JsonAdapter<Status>() {
        override fun fromJson(reader: JsonReader): Status? = try {
            mapToEnum(reader.nextString(), Status::value)
        } catch (exception: Exception) {
            null
        }

        override fun toJson(writer: JsonWriter, value: Status?) {
            writer.value(value?.value)
        }
    }
}

internal inline fun <V, reified K : Enum<K>> mapToEnum(
    value: V,
    property: KProperty1<K, V>
): K = enumValues<K>()
    .associateBy(property)[value]
    ?: throw IllegalArgumentException("Enum value not found ${K::class.java.name}: $value")
