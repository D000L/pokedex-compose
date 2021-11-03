package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonSpecies @Inject constructor(private val pokemonRepository: PokemonRepository) :
  BaseParamsUseCase<Int, PokemonSpecies>() {

  override suspend fun execute(params: Int) = pokemonRepository.getPokemonSpecies(params)
}
