package com.doool.pokedex.domain.usecase.search

import com.doool.pokedex.domain.repository.SearchRepository
import com.doool.pokedex.domain.usecase.LoadState
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class SearchPokemon @Inject constructor(private val searchRepository: SearchRepository) {

  suspend operator fun invoke(query: String?, limit: Int = -1) = flow {
    emit(LoadState.Loading)
    val result = searchRepository.searchPokemon(query, limit).mapLatest { LoadState.Complete(it) }
    emitAll(result)
  }
}