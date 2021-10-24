package com.doool.pokedex.domain.model

data class PokemonSpecies(
  val id: Int = 0,
  val name: String = "",
  val names: List<LocalizedString> = emptyList(),
  val color: Info = Info(),
  val maleRate: Int = 0,
  val femaleRate: Int = 0,
  val evolutionUrl: String = "",
  val flavorText: List<LocalizedString> = listOf(),
  val generation: Info = Info(),
  val genera: List<LocalizedString> = emptyList(),
  val eggGroups: List<Info> = listOf()
)