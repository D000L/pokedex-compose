package com.doool.pokedex.data.response.common

import androidx.annotation.Keep

@Keep
data class Names(
  val language: InfoResponse = InfoResponse(),
  val name: String = ""
)