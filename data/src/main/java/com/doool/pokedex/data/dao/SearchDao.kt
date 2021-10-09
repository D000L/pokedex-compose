package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.doool.pokedex.data.entity.PokemonMoveEntity

@Dao
interface SearchDao {

  @Query("SELECT * FROM pokemon_move WHERE name LIKE '%' || :query || '%' ORDER by name ASC LIMIT 4")
  suspend fun searchPokemonMove(query: String): List<PokemonMoveEntity>

}