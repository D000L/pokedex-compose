package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.SearchDao
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchDao: SearchDao) :
  SearchRepository {

  override fun searchMove(query: String?): Flow<List<PokemonMove>> {
    return searchDao.searchPokemonMove(query ?: "").map { it.map { it.toModel() } }
      .flowOn(Dispatchers.IO)
  }

  override fun searchItem(query: String?): Flow<List<Item>> {
    return searchDao.searchItem(query ?: "").map { it.map { it.toModel() } }.flowOn(Dispatchers.IO)
  }
}
