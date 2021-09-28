package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonEvolutionChain @Inject constructor(private val pokemonRepository: PokemonRepository) {

  suspend operator fun invoke(id: Int): Result<List<PokemonEvolutionChain>> {
    return try {
      Result.success(pokemonRepository.getPokemonEvolutionChain(id))
    } catch (e: Throwable) {
      Result.failure(e)
    }
  }
}