package com.doool.pokedex.data.response.common

import kotlinx.serialization.Serializable

@Serializable
data class Names(
    val language: InfoResponse = InfoResponse(),
    val name: String = ""
)