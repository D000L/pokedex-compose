package com.doool.pokedex.data.response

import androidx.annotation.Keep

@Keep
data class PokemonNewsResponse(
  val title: String = "",
  val shortDescription: String = "",
  val url: String = "",
  val image: String = "",
  val date: String = "",
  val tags: String = ""
)