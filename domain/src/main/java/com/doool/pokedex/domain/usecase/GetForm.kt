package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.Form
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetForm @Inject constructor(private val pokemonRepository: PokemonRepository) :
  BaseParamsUseCase<String, Form>() {

  override suspend fun execute(params: String): Form = pokemonRepository.getForm(params)
}