package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class DownloadAllData @Inject constructor(private val pokemonRepository: PokemonRepository) {

  suspend operator fun invoke(): Result<Unit> {
    return try {
      pokemonRepository.downloadAllPokemon(1, 2)

      Result.success(Unit)
    } catch (e: Throwable) {
      Result.failure(e)
    }
  }
}