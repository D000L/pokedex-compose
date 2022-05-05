package com.doool.pokedex.data.response.common

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FlavorText(
    @SerializedName("flavor_text") val flavorText: String = "",
    val language: InfoResponse = InfoResponse(),
    val version: InfoResponse = InfoResponse()
)
