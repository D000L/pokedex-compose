package com.doool.pokedex.domain.usecase.fetch

import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class FetchMove @Inject constructor(private val pokemonRepository: PokemonRepository) {

  suspend operator fun invoke(name: String) = pokemonRepository.fetchMove(name)
}