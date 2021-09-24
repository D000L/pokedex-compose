package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doool.pokedex.data.entity.PokemonDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDetailDao {

  @Query("SELECT * FROM pokemon_detail")
  fun getAllPokemon(): Flow<List<PokemonDetailEntity>>

  @Query("SELECT * FROM pokemon_detail WHERE name LIKE '%' || :query || '%'")
  fun getPokemon(query : String): Flow<List<PokemonDetailEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonDetail(pokemonDetail: PokemonDetailEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonDetail(pokemonDetail: List<PokemonDetailEntity>)
}