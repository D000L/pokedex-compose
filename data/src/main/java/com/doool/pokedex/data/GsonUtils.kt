package com.doool.pokedex.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> T.toJson(): String {
    return Json.encodeToString(this)
}

inline fun <reified T> String.toResponse(): T {
    return Json.decodeFromString(this)
}

fun String.parseId() = this.trimEnd('/').split("/").last().toInt()