package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemon @Inject constructor(private val pokemonRepository: PokemonRepository) {

  suspend operator fun invoke(id: Int): Result<Pokemon> {
    return try {
      Result.success(pokemonRepository.getPokemon(id))
    } catch (e: Throwable) {
      Result.failure(e)
    }
  }
}