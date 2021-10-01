package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonEvolutionChain @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(id: Int): Flow<List<PokemonEvolutionChain>> = flow {
    val result = pokemonRepository.getPokemonEvolutionChain(id)
    result.forEach {
      it.from.url = pokemonRepository.getPokemonThumbnail(it.from.name)
      it.to.url = pokemonRepository.getPokemonThumbnail(it.to.name)
    }
    emit(result)
  }
}