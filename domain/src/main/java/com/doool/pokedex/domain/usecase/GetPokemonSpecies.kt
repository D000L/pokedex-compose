package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonSpecies @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(id: Int): Flow<PokemonSpecies> = flow {
    emit(pokemonRepository.getPokemonSpecies(id))
  }
}