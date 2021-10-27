package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.doool.pokedex.domain.model.Info

@Dao
interface SearchDao {
  @Query("SELECT id, name FROM pokemon_detail WHERE name LIKE '%' || :query || '%' ORDER BY id ASC LIMIT :limit")
  suspend fun searchPokemonNames(query: String = "", limit: Int = -1): List<Info>

  @Query("SELECT id, name FROM pokemon_move WHERE name LIKE '%' || :query || '%' ORDER BY id ASC LIMIT :limit")
  suspend fun searchMoveNames(query: String = "", limit: Int = -1): List<Info>

  @Query("SELECT id, name FROM item WHERE name LIKE '%' || :query || '%' ORDER BY id ASC LIMIT :limit")
  suspend fun searchItemNames(query: String = "", limit: Int = -1): List<Info>
}