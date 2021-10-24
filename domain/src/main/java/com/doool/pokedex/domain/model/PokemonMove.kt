package com.doool.pokedex.domain.model

data class PokemonMove(
  val id: Int = -1,
  val name: String = "",
  val names: List<LocalizedString> = listOf(LocalizedString(name)),
  val accuracy: Int = 0,
  val damageClass: Info = Info(),
  val flavorTextEntries: List<LocalizedString> = emptyList(),
  val effectEntries: Effect = Effect(),
  val learnedPokemon: List<Info> = emptyList(),
  val machines: Info = Info(),
  val power: Int = 0,
  val pp: Int = 0,
  val type: Info = Info()
)

data class Effect(
  val effect: String = "",
  val shortEffect: String = "",
  val effectChance: Int = 0
)


