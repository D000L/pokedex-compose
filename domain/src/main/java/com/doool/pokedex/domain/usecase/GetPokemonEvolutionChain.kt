package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonEvolutionChain @Inject constructor(private val pokemonRepository: PokemonRepository) {

  suspend operator fun invoke(id: Int): Result<List<PokemonEvolutionChain>> {
    return try {
      val result = pokemonRepository.getPokemonEvolutionChain(id)
      result.forEach {
        it.from.url = pokemonRepository.getPokemonThumbnail(it.from.name)
        it.to.url = pokemonRepository.getPokemonThumbnail(it.to.name)
      }

      Result.success(result)
    } catch (e: Throwable) {
      Result.failure(e)
    }
  }
}