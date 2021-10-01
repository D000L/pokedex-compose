package com.doool.pokedex.domain.model

data class PokemonEvolutionChain(
  val from: Info = Info(),
  val to: Info = Info(),
  val condition: Condition = Condition(),
)

data class Condition(
  val item: Info? = null,
  val minLevel: Int = 0,
  val timeOfDay: String = "",
  val trigger: Info = Info(),
)