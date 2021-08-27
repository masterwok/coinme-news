package com.masterwok.coinme.common.extensions

import java.text.SimpleDateFormat
import java.util.*

private val iso8601Format = SimpleDateFormat("yyyy-MM-dd", Locale.US)

/**
 * Convert the [Date] instance to an [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601) formatted
 * string.
 */
fun Date.toIso8601(): String = iso8601Format.format(this)