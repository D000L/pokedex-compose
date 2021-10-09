package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonMove
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

  fun searchMove(query: String? = null): Flow<List<PokemonMove>>
  fun searchItem(query: String? = null): Flow<List<Item>>
}