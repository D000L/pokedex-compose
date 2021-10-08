package com.doool.pokedex.domain.model

data class PokemonMove(
  val id: Int = 0,
  val name: String = "",
  val accuracy: Int = 0,
  val damageClass: Info = Info(),
  val flavorTextEntries: List<String> = emptyList(),
  val effectEntries: Effect = Effect(),
  val power: Int = 0,
  val pp: Int = 0,
  val type: Info = Info()
)

data class Effect(
  val effect: String = "",
  val shortEffect: String = "",
  val effectChance: Int = 0
)


