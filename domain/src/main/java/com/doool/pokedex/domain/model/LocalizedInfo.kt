package com.doool.pokedex.domain.model

data class LocalizedInfo(
  val name: String = "",
  var names: List<LocalizedString> = emptyList(),
  var url: String = ""
)