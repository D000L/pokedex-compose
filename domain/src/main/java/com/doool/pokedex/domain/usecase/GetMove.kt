package com.doool.pokedex.domain.usecase

import androidx.annotation.WorkerThread
import com.doool.pokedex.domain.networkBoundResource
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetMove @Inject constructor(private val pokemonRepository: PokemonRepository) {

  @WorkerThread
  operator fun invoke(name: String) = networkBoundResource(query = {
    pokemonRepository.getPokemonMove(name)
  }, fetch = {
    pokemonRepository.fetchMove(listOf(it.name))
  }, shouldFetch = {
    it.id == -1
  })
}