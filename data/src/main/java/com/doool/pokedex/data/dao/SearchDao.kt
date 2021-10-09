package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.doool.pokedex.data.entity.ItemEntity
import com.doool.pokedex.data.entity.PokemonMoveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

  @Query("SELECT * FROM pokemon_move WHERE name LIKE '%' || :query || '%' ORDER by name ASC LIMIT 4")
  fun searchPokemonMove(query: String): Flow<List<PokemonMoveEntity>>

  @Query("SELECT * FROM item WHERE name LIKE '%' || :query || '%' ORDER by name ASC LIMIT 4")
  fun searchItem(query: String): Flow<List<ItemEntity>>
}