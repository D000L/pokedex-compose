package com.doool.pokedex.data.response.common

import androidx.annotation.Keep

@Keep
data class InfoResponse(
    val name: String = "",
    val url: String = ""
)