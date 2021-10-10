package com.doool.pokedex.data.entity

import androidx.room.Entity

@Entity(tableName = "pokemon_detail", primaryKeys = ["name", "index"])
data class PokemonDetailEntity(
  val name: String,
  val index: Int,
  val id: Int = -1,
  val json: String? = null
)