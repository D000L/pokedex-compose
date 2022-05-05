package com.doool.pokedex.data.response.common

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Sprites(
    val default: String = "",
    @SerializedName("front_default") val frontDefault: String = "",
    @SerializedName("back_default") val backDefault: String = "",
    val other: SpritesOthers = SpritesOthers()
)

@Keep
data class SpritesOthers(
    @SerializedName("official-artwork") val artwork: Artwork = Artwork(),
) {
    @Keep
    data class Artwork(
        @SerializedName("front_default") val frontDefault: String? = null,
    )
}