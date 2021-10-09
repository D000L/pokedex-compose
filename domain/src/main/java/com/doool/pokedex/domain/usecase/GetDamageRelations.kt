package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.Damage
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDamageRelations @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(typeNames: List<String>): Flow<List<Damage>> = flow {
    emit(
      typeNames.flatMap {
        pokemonRepository.getPokemonTypeResistance(it).damageRelations
      }.distinctBy { it.type }
    )
  }
}

