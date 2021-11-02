package com.doool.pokedex.domain.usecase

import androidx.annotation.WorkerThread
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMove @Inject constructor(private val pokemonRepository: PokemonRepository) {

  @WorkerThread
  operator fun invoke(name: String) = flow {
    emit(pokemonRepository.getPokemonMove(name))
  }.flowOn(Dispatchers.IO)
}