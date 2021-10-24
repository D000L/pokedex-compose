package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.IndexedItem
import com.doool.pokedex.domain.repository.SearchRepository
import javax.inject.Inject

class GetPokemonNames @Inject constructor(private val searchRepository: SearchRepository) {

  suspend operator fun invoke(query: String? = null): List<IndexedItem> =
    searchRepository.searchPokemonNames(query)
}


