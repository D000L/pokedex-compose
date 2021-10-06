package com.doool.pokedex.domain.model


data class PokemonTypeResistance(
  val id: Int = 0,
  val name: String = "",
  val damageRelations: List<Damage>
)

data class Damage(
  val type: String = "",
  val amount: Float = 0f
)