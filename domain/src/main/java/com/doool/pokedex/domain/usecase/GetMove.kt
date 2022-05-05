package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetMove @Inject constructor(private val pokemonRepository: PokemonRepository) :
    BaseParamsUseCase<String, PokemonMove>() {

    override suspend fun execute(params: String): PokemonMove =
        pokemonRepository.getPokemonMove(params)
}