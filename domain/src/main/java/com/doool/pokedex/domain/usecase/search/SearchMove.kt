package com.doool.pokedex.domain.usecase.search

import androidx.annotation.WorkerThread
import com.doool.pokedex.domain.networkBoundResources
import com.doool.pokedex.domain.repository.PokemonRepository
import com.doool.pokedex.domain.repository.SearchRepository
import javax.inject.Inject

class SearchMove @Inject constructor(
  private val searchRepository: SearchRepository,
  private val pokemonRepository: PokemonRepository
) {

  @WorkerThread
  suspend operator fun invoke(query: String?, limit: Int = -1) = networkBoundResources(query = {
    searchRepository.searchMove(query, limit)
  }, fetch = {
    pokemonRepository.fetchMove(it.map { it.name })
  }, shouldFetch = {
    it.isPlaceholder
  })
}