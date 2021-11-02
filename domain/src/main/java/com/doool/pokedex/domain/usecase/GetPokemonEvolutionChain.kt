package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.Urls
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonEvolutionChain @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(url: String): Flow<List<PokemonEvolutionChain>> = flow {
    val result = pokemonRepository.getPokemonEvolutionChain(url)
    result.forEach {
      val fromSprite = Urls.getImageUrl(it.from.id)
      it.from.names = pokemonRepository.getPokemonSpecies(it.from.id).names
      it.from.imageUrl = fromSprite

      val toSprite = Urls.getImageUrl(it.to.id)
      it.to.names = pokemonRepository.getPokemonSpecies(it.from.id).names
      it.to.imageUrl = toSprite
    }
    emit(result)
  }
}