package com.doool.pokedex.domain.repository

import androidx.paging.DataSource
import com.doool.pokedex.domain.model.*

interface PokemonRepository {

  fun searchPokemonList(query: String?): DataSource.Factory<Int, PokemonDetail>

  suspend fun getPokemon(id: Int): PokemonDetail
  suspend fun getPokemonSpecies(id: Int): PokemonSpecies
  suspend fun getPokemonEvolutionChain(url: String): List<PokemonEvolutionChain>
  suspend fun getPokemonTypeResistance(name: String): PokemonTypeResistance
  suspend fun getPokemonMove(name: String): PokemonMove

  suspend fun getPokemonThumbnail(name: String): String
}