package com.doool.pokedex.domain.usecase.search

import androidx.annotation.WorkerThread
import com.doool.pokedex.domain.repository.PokemonRepository
import com.doool.pokedex.domain.repository.SearchRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchPokemon @Inject constructor(
  private val searchRepository: SearchRepository,
  private val pokemonRepository: PokemonRepository
) {

  @WorkerThread
  suspend operator fun invoke(query: String?, limit: Int = -1) = flow {
    emit(searchRepository.searchPokemonNames(query, limit).map { pokemonRepository.getPokemon(it) })
  }
}