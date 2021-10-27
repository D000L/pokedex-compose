package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.Urls
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.repository.SearchRepository
import javax.inject.Inject

class GetPokemonNames @Inject constructor(private val searchRepository: SearchRepository) {

  suspend operator fun invoke(query: String? = null): List<Pokemon> =
    searchRepository.searchPokemonNames(query).map {
      Pokemon(it.id, it.name, Urls.getImageUrl(it.id))
    }
}


