package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonEvolutionChain @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(url: String): Flow<List<PokemonEvolutionChain>> = flow {
    val result = pokemonRepository.getPokemonEvolutionChain(url)
    result.forEach {
      val fromId = it.from.url.trimEnd('/').split("/").last().toInt()
      val fromSprite =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$fromId.png"
      it.from.names = pokemonRepository.getPokemonSpecies(it.from.name).names
      it.from.url = fromSprite

      val toId = it.to.url.trimEnd('/').split("/").last().toInt()
      val toSprite =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$toId.png"

      it.to.names = pokemonRepository.getPokemonSpecies(it.to.name).names
      it.to.url = toSprite
    }
    emit(result)
  }
}