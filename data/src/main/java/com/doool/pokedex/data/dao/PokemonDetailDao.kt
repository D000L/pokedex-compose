package com.doool.pokedex.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doool.pokedex.data.entity.PokemonDetailEntity

@Dao
interface PokemonDetailDao {

  @Query("SELECT * FROM pokemon_detail")
   fun getAllPokemon(): DataSource.Factory<Int, PokemonDetailEntity>

  @Query("SELECT * FROM pokemon_detail WHERE name LIKE '%' || :query || '%'")
   fun searchPokemonList(query: String): DataSource.Factory<Int, PokemonDetailEntity>

  @Query("SELECT * FROM pokemon_detail WHERE id = :id")
  suspend fun getPokemon(id: Int): PokemonDetailEntity

  @Query("SELECT * FROM pokemon_detail WHERE name = :name")
  suspend fun getPokemon(name: String): PokemonDetailEntity

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonDetail(pokemonDetail: List<PokemonDetailEntity>)
}