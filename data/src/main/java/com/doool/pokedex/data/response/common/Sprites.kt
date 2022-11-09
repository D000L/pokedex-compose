package com.doool.pokedex.data.response.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sprites(
    val default: String = "",
    @SerialName("front_default") val frontDefault: String = "",
    @SerialName("back_default") val backDefault: String = "",
    val other: SpritesOthers = SpritesOthers()
)

@Serializable
data class SpritesOthers(
    @SerialName("official-artwork") val artwork: Artwork = Artwork(),
) {
    @Serializable
    data class Artwork(
        @SerialName("front_default") val frontDefault: String? = null,
    )
}