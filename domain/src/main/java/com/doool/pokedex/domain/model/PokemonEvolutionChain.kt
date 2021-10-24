package com.doool.pokedex.domain.model

data class PokemonEvolutionChain(
  val from: LocalizedInfo = LocalizedInfo(),
  val to: LocalizedInfo = LocalizedInfo(),
  val condition: Condition = Condition(),
)

data class Condition(
  val item: Info? = null,
  val minLevel: Int = 0,
  val timeOfDay: String = "",
  val trigger: Info = Info(),
)