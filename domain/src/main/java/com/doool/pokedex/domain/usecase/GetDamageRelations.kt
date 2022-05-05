package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.model.Damage
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetDamageRelations @Inject constructor(private val pokemonRepository: PokemonRepository) :
    BaseParamsUseCase<List<String>, List<Damage>>() {

    override suspend fun execute(params: List<String>): List<Damage> {
        val map = mutableMapOf<String, Float>()
        params.flatMap {
            pokemonRepository.getPokemonTypeResistance(it).damageRelations
        }.forEach {
            val current = map.getOrPut(it.type) { 1f }
            map[it.type] = current * it.amount
        }
        return map.map { (key, value) -> Damage(key, value) }.toList()
    }
}