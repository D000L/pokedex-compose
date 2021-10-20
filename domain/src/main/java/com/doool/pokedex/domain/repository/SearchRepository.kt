package com.doool.pokedex.domain.repository

interface SearchRepository {

  suspend fun searchPokemonNames(query: String? = null, limit: Int = -1): List<String>
  suspend fun searchMoveNames(query: String? = null, limit: Int = -1): List<String>
  suspend fun searchItem(query: String? = null, limit: Int = -1): List<String>
}