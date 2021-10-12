package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonMove
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

  fun searchPokemon(query: String? = null, limit: Int = -1): Flow<List<PokemonDetail>>
  fun searchMove(query: String? = null, limit: Int = -1): Flow<List<PokemonMove>>
  fun searchItem(query: String? = null, limit: Int = -1): Flow<List<Item>>

  suspend fun searchPokemonNames(query: String?): List<String>
  suspend fun searchMoveNames(query: String?): List<String>
}