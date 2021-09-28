package com.doool.pokedex.domain.model

data class PokemonDetail(
  val id: Int,
  val name: String,
  val height: Int,
  val weight: Int,
  val image: String,
  val stats: List<Stat>,
  val types: List<Info>
)

data class Stat(
  val amount: Int,
  val name: String,
  val infoUrl: String,
)