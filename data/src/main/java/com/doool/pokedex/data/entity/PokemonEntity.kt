package com.doool.pokedex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
  @PrimaryKey var offset: Int,
  val name: String,
  val url: String
)