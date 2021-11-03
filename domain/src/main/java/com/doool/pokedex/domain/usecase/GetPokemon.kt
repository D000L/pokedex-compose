package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemon @Inject constructor(private val pokemonRepository: PokemonRepository) :
  BaseParamsUseCase<String, PokemonDetail>() {

  override suspend fun execute(params: String): PokemonDetail = pokemonRepository.getPokemon(params)
}