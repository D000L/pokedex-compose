package com.doool.pokedex.domain.model

data class PokemonSpecies(
  val id: Int,
  val name: String,
  val color: Info,
  val evolutionUrl: String,
  val flavorText: List<String>,
  val generation: Info
)