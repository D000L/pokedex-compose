package com.doool.pokedex.domain.repository

import com.doool.pokedex.domain.model.Info

interface SearchRepository {

  suspend fun searchPokemonNames(query: String? = null, limit: Int = -1): List<Info>
  suspend fun searchMoveNames(query: String? = null, limit: Int = -1): List<Info>
  suspend fun searchItem(query: String? = null, limit: Int = -1): List<Info>
}