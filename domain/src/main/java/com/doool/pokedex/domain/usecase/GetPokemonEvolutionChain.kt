package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.Urls
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonEvolutionChain @Inject constructor(private val pokemonRepository: PokemonRepository) :
    BaseParamsUseCase<String, List<PokemonEvolutionChain>>() {

    override suspend fun execute(params: String): List<PokemonEvolutionChain> {
        val result = pokemonRepository.getPokemonEvolutionChain(params)
        result.forEach {
            val fromSprite = Urls.getImageUrl(it.from.id)
            it.from.names = pokemonRepository.getPokemonSpecies(it.from.id).names
            it.from.imageUrl = fromSprite

            val toSprite = Urls.getImageUrl(it.to.id)
            it.to.names = pokemonRepository.getPokemonSpecies(it.from.id).names
            it.to.imageUrl = toSprite
        }
        return result
    }
}