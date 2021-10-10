package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonMove @Inject constructor(private val pokemonRepository: PokemonRepository){

  operator fun invoke(name: String): Flow<PokemonMove> = flow {
    emit(PokemonMove())//pokemonRepository.getPokemonMove(name))
  }
}