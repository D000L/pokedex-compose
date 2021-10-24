package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.IndexedItem

interface SearchRepository {

  suspend fun searchPokemonNames(query: String? = null, limit: Int = -1): List<IndexedItem>
  suspend fun searchMoveNames(query: String? = null, limit: Int = -1): List<IndexedItem>
  suspend fun searchItem(query: String? = null, limit: Int = -1): List<IndexedItem>
}