package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonSpecies
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

  suspend fun getAllPokemon(): List<PokemonDetail>
  suspend fun searchPokemonList(query: String): List<PokemonDetail>

  suspend fun getPokemon(id: Int): PokemonDetail
  suspend fun getPokemonSpecies(id: Int): PokemonSpecies
  suspend fun getPokemonEvolutionChain(id: Int): List<PokemonEvolutionChain>

  suspend fun getPokemonThumbnail(name: String): String
}