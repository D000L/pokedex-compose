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
  suspend fun getAllPokemon(): List<PokemonDetailEntity>

  @Query("SELECT * FROM pokemon_detail WHERE name LIKE '%' || :query || '%'")
  suspend fun searchPokemonList(query: String): List<PokemonDetailEntity>

  @Query("SELECT * FROM pokemon_detail WHERE id = :id")
  suspend fun getPokemon(id: Int): PokemonDetailEntity

  @Query("SELECT * FROM pokemon_detail WHERE name = :name")
  suspend fun getPokemon(name: String): PokemonDetailEntity

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonDetail(pokemonDetail: List<PokemonDetailEntity>)
}