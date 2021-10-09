package com.doool.pokedex.domain.usecase.search

import com.doool.pokedex.domain.repository.SearchRepository
import com.doool.pokedex.domain.usecase.LoadState
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class SearchMove @Inject constructor(private val searchRepository: SearchRepository) {

  suspend operator fun invoke(query: String?) = flow {
    emit(LoadState.Loading)
    val result = searchRepository.searchMove(query).mapLatest { LoadState.Complete(it) }
    emitAll(result)
  }
}