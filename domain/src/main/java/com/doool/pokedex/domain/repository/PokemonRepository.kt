package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.*

interface PokemonRepository {

  suspend fun getPokemon(name: String): PokemonDetail
  suspend fun getPokemonSpecies(name: String): PokemonSpecies
  suspend fun getPokemonEvolutionChain(url: String): List<PokemonEvolutionChain>
  suspend fun getPokemonTypeResistance(name: String): PokemonTypeResistance
  suspend fun getPokemonMove(name: String): PokemonMove

  suspend fun getPokemonThumbnail(name: String): String

  suspend fun fetchItem(names: List<String>)
  suspend fun fetchMove(names: List<String>)
  suspend fun fetchPokemon(names: List<String>)
}