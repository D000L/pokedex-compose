package com.doool.pokedex.data.mapper

import com.doool.pokedex.data.response.DamageRelationsResponse
import com.doool.pokedex.data.response.PokemonTypeResistanceResponse
import com.doool.pokedex.domain.model.Damage
import com.doool.pokedex.domain.model.PokemonTypeResistance

fun PokemonTypeResistanceResponse.toModel(): PokemonTypeResistance = with(this) {
    PokemonTypeResistance(
        id = id,
        name = name,
        damageRelations = damageRelations.toModel()
    )
}

fun DamageRelationsResponse.toModel(): List<Damage> = with(this) {
    val list = mutableListOf<Damage>()
    doubleDamageFrom.map { list.add(Damage(it.name, 2f)) }
    halfDamageFrom.map { list.add(Damage(it.name, 0.5f)) }
    noDamageFrom.map { list.add(Damage(it.name, 0f)) }
    list
}