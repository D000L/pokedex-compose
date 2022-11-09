package com.doool.pokedex.data.response.common

import kotlinx.serialization.Serializable

@Serializable
data class ListItem<T>(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<T> = emptyList()
)