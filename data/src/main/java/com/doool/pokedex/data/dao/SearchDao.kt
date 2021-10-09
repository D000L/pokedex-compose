package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.doool.pokedex.data.entity.ItemEntity
import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.PokemonMoveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

  @Query("SELECT * FROM pokemon_detail WHERE name LIKE '%' || :query || '%' ORDER by name ASC LIMIT :limit")
  fun searchPokemon(query: String, limit: Int = -1): Flow<List<PokemonDetailEntity>>

  @Query("SELECT * FROM pokemon_move WHERE name LIKE '%' || :query || '%' ORDER by name ASC LIMIT :limit")
  fun searchPokemonMove(query: String, limit: Int = -1): Flow<List<PokemonMoveEntity>>

  @Query("SELECT * FROM item WHERE name LIKE '%' || :query || '%' ORDER by name ASC LIMIT :limit")
  fun searchItem(query: String, limit: Int = -1): Flow<List<ItemEntity>>
}