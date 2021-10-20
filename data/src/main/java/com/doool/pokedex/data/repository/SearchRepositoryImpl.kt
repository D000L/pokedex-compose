package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.SearchDao
import com.doool.pokedex.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchDao: SearchDao) :
  SearchRepository {

  override suspend fun searchPokemonNames(query: String?, limit: Int): List<String> {
    return searchDao.searchPokemonNames(query ?: "", limit)
  }

  override suspend fun searchMoveNames(query: String?, limit: Int): List<String> {
    return searchDao.searchMoveNames(query ?: "", limit)
  }

  override suspend fun searchItem(query: String?, limit: Int): List<String> {
    return searchDao.searchItemNames(query ?: "", limit)
  }
}
