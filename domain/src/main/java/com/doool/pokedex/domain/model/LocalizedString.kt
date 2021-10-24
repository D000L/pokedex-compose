package com.doool.pokedex.domain.model

data class LocalizedString(
  val text : String,
  val language: String = "en"
)