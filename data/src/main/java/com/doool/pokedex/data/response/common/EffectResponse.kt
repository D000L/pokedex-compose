package com.doool.pokedex.data.response.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EffectResponse(
    val effect: String = "",
    val language: InfoResponse = InfoResponse(),
    @SerialName("short_effect") val shortEffect: String = ""
)