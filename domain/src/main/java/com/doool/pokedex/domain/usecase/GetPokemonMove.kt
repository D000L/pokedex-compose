package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.networkBoundResource
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonMove @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(name: String) = networkBoundResource(query = {
    pokemonRepository.getPokemonMove(name)
  }, fetch = {
    pokemonRepository.fetchMove(listOf(it.name))
  }, shouldFetch = {
    it.isPlaceholder
  })
}