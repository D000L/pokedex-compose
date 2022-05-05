package com.doool.pokedex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_evolution_chain")
data class PokemonEvolutionChainEntity(
    @PrimaryKey val id: Int,
    val json: String,
)

