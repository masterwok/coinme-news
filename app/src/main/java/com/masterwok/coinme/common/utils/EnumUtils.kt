package com.masterwok.coinme.common.utils

import kotlin.reflect.KProperty1

internal inline fun <V, reified K : Enum<K>> mapToEnum(
    value: V,
    property: KProperty1<K, V>
): K = enumValues<K>()
    .associateBy(property)[value]
    ?: throw IllegalArgumentException("Enum value not found ${K::class.java.name}: $value")
