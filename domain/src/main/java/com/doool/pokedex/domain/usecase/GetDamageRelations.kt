package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.Damage
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDamageRelations @Inject constructor(private val pokemonRepository: PokemonRepository) {

  operator fun invoke(typeNames: List<String>): Flow<List<Damage>> = flow {
    val map = mutableMapOf<String, Float>()
    typeNames.flatMap {
      pokemonRepository.getPokemonTypeResistance(it).damageRelations
    }.forEach {
      val current = map.getOrPut(it.type) { 1f }
      map[it.type] = current * it.amount
    }
    emit(map.map { (key, value) -> Damage(key, value) }.toList())
  }
}

