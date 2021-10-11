package com.doool.pokedex.domain.usecase

import androidx.annotation.WorkerThread
import com.doool.pokedex.domain.networkBoundResource
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

@WorkerThread
class GetPokemon @Inject constructor(private val pokemonRepository: PokemonRepository) {

  @WorkerThread
  operator fun invoke(name: String) = networkBoundResource(query = {
    pokemonRepository.getPokemon(name)
  }, fetch = {
    pokemonRepository.fetchPokemon(listOf(it.name))
  }, shouldFetch = {
    it.isPlaceholder
  })
}