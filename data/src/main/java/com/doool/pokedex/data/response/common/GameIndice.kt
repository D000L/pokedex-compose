package com.doool.pokedex.data.response.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameIndice(
    @SerialName("game_index") val gameIndex: Int = 0,
    val version: InfoResponse = InfoResponse()
)
