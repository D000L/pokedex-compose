package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.repository.PokemonRepository
import com.doool.pokedex.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchPokemonMove @Inject constructor(
  private val searchRepository: SearchRepository,
  private val pokemonRepository: PokemonRepository
) {

  operator fun invoke(query: String?): Flow<LoadState<List<PokemonMove>>> = flow {
    emit(LoadState.Loading)

    val result = searchRepository.searchMove(query)
    emit(LoadState.Complete(result))

    emit(LoadState.Complete(result.map {
      if (it.isPlaceholder) pokemonRepository.getPokemonMove(it.name)
      else it
    }))
  }
}