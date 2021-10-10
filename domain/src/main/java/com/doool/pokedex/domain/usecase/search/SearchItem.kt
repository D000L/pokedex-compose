package com.doool.pokedex.domain.usecase.search

import androidx.annotation.WorkerThread
import com.doool.pokedex.domain.networkBoundResource
import com.doool.pokedex.domain.repository.PokemonRepository
import com.doool.pokedex.domain.repository.SearchRepository
import javax.inject.Inject

class SearchItem @Inject constructor(
  private val searchRepository: SearchRepository,
  private val pokemonRepository: PokemonRepository
) {

  @WorkerThread
  suspend operator fun invoke(query: String?, limit: Int = -1) = networkBoundResource(query = {
    searchRepository.searchItem(query, limit)
  }, fetch = {
    pokemonRepository.fetchItem(it.map { it.name })
  }, shouldFetch = {
    it.isPlaceholder
  })
}