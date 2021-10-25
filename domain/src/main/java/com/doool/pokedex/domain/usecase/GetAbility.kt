package com.doool.pokedex.domain.usecase

import androidx.annotation.WorkerThread
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAbility @Inject constructor(private val pokemonRepository: PokemonRepository) {

  @WorkerThread
  operator fun invoke(name: String) = flow {
    emit(pokemonRepository.getAbility(name))
  }
}