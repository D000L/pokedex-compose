package com.doool.pokedex.domain.model

data class PokemonSpecies(
  val id: Int = 0,
  val name: String = "",
  val color: Info = Info(),
  val evolutionUrl: String = "",
  val flavorText: List<String> = listOf(),
  val generation: Info = Info(),
  val genera: Genera = Genera()
)

data class Genera(
  val genus: String = ""
)