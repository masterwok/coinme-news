package com.masterwok.coinme.common.extensions

import android.content.Context
import androidx.core.os.ConfigurationCompat
import java.util.*

/**
 * Get the current default [Locale] of this device.
 */
val Context.currentLocale: Locale get() = ConfigurationCompat.getLocales(resources.configuration)[0]
