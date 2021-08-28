package com.masterwok.coinme.data.clients.moshi.adapters

import android.net.Uri
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class UriJsonAdapter : JsonAdapter<Uri>() {

    override fun fromJson(reader: JsonReader): Uri? = Uri.parse(reader.nextString())

    override fun toJson(writer: JsonWriter, value: Uri?) {
        writer.value(value?.toString())
    }
}