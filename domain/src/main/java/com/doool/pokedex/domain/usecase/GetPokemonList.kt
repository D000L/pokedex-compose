package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonList @Inject constructor(private val pokemonRepository: PokemonRepository) {

  suspend operator fun invoke(query: String? = null): Result<List<PokemonDetail>> =
    try {
      val result = if (query == null) pokemonRepository.getAllPokemon()
      else pokemonRepository.searchPokemonList(query)
      Result.success(result)
    } catch (e: Throwable) {
      Result.failure(e)
    }
}