package com.doool.pokedex.data.response.common

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EffectResponse(
    val effect: String = "",
    val language: InfoResponse = InfoResponse(),
    @SerializedName("short_effect") val shortEffect: String = ""
)