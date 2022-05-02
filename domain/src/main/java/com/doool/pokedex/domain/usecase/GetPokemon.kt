package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemon @Inject constructor(private val pokemonRepository: PokemonRepository) :
    BaseParamsUseCase<GetPokemon.Params, PokemonDetail>() {

    sealed class Params {
        class ByName(val name: String) : Params()
        class ById(val Id: Int) : Params()
    }

    override suspend fun execute(params: Params): PokemonDetail {
        return when (params) {
            is Params.ById -> pokemonRepository.getPokemon(params.Id)
            is Params.ByName -> pokemonRepository.getPokemon(params.name)
        }
    }
}