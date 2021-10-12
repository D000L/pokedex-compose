package com.doool.pokedex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_move")
data class PokemonMoveEntity(
  @PrimaryKey val name: String,
  val index: Int,
  val id: Int = -1,
  val json: String? = null
)