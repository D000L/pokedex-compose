package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SearchDao {
  @Query("SELECT name FROM pokemon_detail WHERE name LIKE '%' || :query || '%' ORDER BY `index` ASC LIMIT :limit")
  suspend fun searchPokemonNames(query: String = "", limit: Int = -1): List<String>

  @Query("SELECT name FROM pokemon_move WHERE name LIKE '%' || :query || '%' ORDER BY `index` ASC LIMIT :limit")
  suspend fun searchMoveNames(query: String = "", limit: Int = -1): List<String>

  @Query("SELECT name FROM item WHERE name LIKE '%' || :query || '%' ORDER BY `index` ASC LIMIT :limit")
  suspend fun searchItemNames(query: String = "", limit: Int = -1): List<String>
}