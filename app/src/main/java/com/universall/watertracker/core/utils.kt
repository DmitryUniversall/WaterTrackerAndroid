package com.universall.watertracker.core

import kotlin.enums.enumEntries

inline fun <reified T> enumFromId(id: Int): T? where T : Enum<T>, T : HasId {
    return enumEntries<T>().firstOrNull { it.id == id }
}

inline fun <reified T> enumFromId(id: Long): T? where T : Enum<T>, T : HasId {
    return enumEntries<T>().firstOrNull { it.id.toLong() == id }
}

fun Double.toTwoDigits(): String = "%.2f".format(this)
