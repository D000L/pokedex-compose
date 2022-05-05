package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.Ability
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetAbility @Inject constructor(private val pokemonRepository: PokemonRepository) :
    BaseParamsUseCase<String, Ability>() {

    override suspend fun execute(params: String): Ability = pokemonRepository.getAbility(params)
}