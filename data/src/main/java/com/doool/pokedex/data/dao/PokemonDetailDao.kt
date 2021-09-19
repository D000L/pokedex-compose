package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doool.pokedex.data.entity.PokemonDetailEntity

@Dao
interface PokemonDetailDao {

  @Query("SELECT * FROM pokemon_detail WHERE name = :name")
  suspend fun getPokemonDetail(name: String): PokemonDetailEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonDetail(pokemonDetail: PokemonDetailEntity)
}