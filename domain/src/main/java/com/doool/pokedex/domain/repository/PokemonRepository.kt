package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonSpecies
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

  suspend fun getPokemonSpecies(id: Int): PokemonSpecies
  suspend fun downloadAllPokemon(startPage: Int, endPage: Int)
  fun getAllPokemon(): Flow<List<PokemonDetail>>
}