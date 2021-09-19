package com.doool.pokedex.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
  @PrimaryKey var offset: Int,
  val name: String,
  val url: String
) {
  private val index
    get() = INDEX_REGEX.find(url)?.groupValues?.getOrNull(1) ?: 0

  val imageUrl
    get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${index}.png"

  companion object{
    private val INDEX_REGEX = Regex("https://pokeapi.co/api/v2/pokemon/([0-9]*)")
  }
}