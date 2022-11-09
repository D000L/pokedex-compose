package com.doool.pokedex.data.response.common

import kotlinx.serialization.Serializable

@Serializable
data class InfoResponse(
    val name: String = "",
    val url: String = ""
)