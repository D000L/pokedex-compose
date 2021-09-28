package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonThumbnail @Inject constructor(private val pokemonRepository: PokemonRepository) {

  suspend operator fun invoke(name: String): Result<String> {
    return try {
      Result.success(pokemonRepository.getSprites(name))
    } catch (e: Throwable) {
      Result.failure(e)
    }
  }
}