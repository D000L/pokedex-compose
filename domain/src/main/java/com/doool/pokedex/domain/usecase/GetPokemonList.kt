package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonList @Inject constructor(private val pokemonRepository: PokemonRepository) {

  suspend operator fun invoke(id: Int): Result<List<Pokemon>> {
    return try {
      Result.success(pokemonRepository.getPokemonList(offset = id))
    } catch (e: Throwable) {
      Result.failure(e)
    }
  }
}