package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.SearchDao
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchDao: SearchDao) :
  SearchRepository {

  override fun searchPokemon(query: String?, limit: Int): Flow<List<PokemonDetail>> {
    return searchDao.searchPokemon(query ?: "", limit).map { it.map { it.toModel() } }
      .flowOn(Dispatchers.IO)
  }

  override fun searchMove(query: String?, limit: Int): Flow<List<PokemonMove>> {
    return searchDao.searchPokemonMove(query ?: "", limit).map { it.map { it.toModel() } }
      .flowOn(Dispatchers.IO)
  }

  override fun searchItem(query: String?, limit: Int): Flow<List<Item>> {
    return searchDao.searchItem(query ?: "", limit).map { it.map { it.toModel() } }
      .flowOn(Dispatchers.IO)
  }
}
