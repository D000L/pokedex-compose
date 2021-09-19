package com.doool.pokedex.domain.model

data class PokemonDetail(
  val name: String,
  val height: Int,
  val weight: Int,
  val image: String,
  val stats: List<Stat>
)

data class Stat(
  val amount: Int,
  val name: String,
  val infoUrl: String,
)