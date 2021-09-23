package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonSpecies @Inject constructor(private val pokemonRepository: PokemonRepository) {

  suspend operator fun invoke(id: Int): Result<PokemonSpecies> {
    return try {
      Result.success(pokemonRepository.getPokemonSpecies(id))
    } catch (e: Throwable) {
      Result.failure(e)
    }
  }
}