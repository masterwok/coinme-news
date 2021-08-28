package com.masterwok.coinme.common.extensions

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getDisplayString(
    locale: Locale,
    pattern: String
): String = DateFormat.getBestDateTimePattern(
    locale,
    pattern
).let { bestPattern -> SimpleDateFormat(bestPattern, locale).format(time) }

fun Calendar.getShortDisplayString(
    locale: Locale
): String = getDisplayString(locale, "MM/dd/yyyy")
