package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.SearchDao
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchDao: SearchDao) :
  SearchRepository {

  override suspend fun searchMove(query: String?): List<PokemonMove> {
    return searchDao.searchPokemonMove(query ?: "").map { it.toModel() }
  }
}
