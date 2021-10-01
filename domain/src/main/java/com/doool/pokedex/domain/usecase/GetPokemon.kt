package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemon @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(id: Int): Flow<PokemonDetail> = flow {
    emit(pokemonRepository.getPokemon(id))
  }
}