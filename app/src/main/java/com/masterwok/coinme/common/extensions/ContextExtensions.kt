package com.masterwok.coinme.common.extensions

import android.app.SearchManager
import android.content.Context
import androidx.core.app.ShareCompat
import androidx.core.os.ConfigurationCompat
import java.util.*

/**
 * Get the current default [Locale] of this device.
 */
val Context.currentLocale: Locale get() = ConfigurationCompat.getLocales(resources.configuration)[0]

/**
 * Share a URL using the Android chooser.
 */
fun Context.shareUrl(url: String) = ShareCompat
    .IntentBuilder(this)
    .setType("text/plain")
    .setText(url)
    .startChooser();

/**
 * Get the system [SearchManager].
 */
val Context.searchManager get() = getSystemService(Context.SEARCH_SERVICE) as SearchManager
