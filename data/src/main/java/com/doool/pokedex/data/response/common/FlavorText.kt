package com.doool.pokedex.data.response.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlavorText(
    @SerialName("flavor_text") val flavorText: String = "",
    val language: InfoResponse = InfoResponse(),
    val version: InfoResponse = InfoResponse()
)
