package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doool.pokedex.data.entity.PokemonEntity

@Dao
interface PokemonDao {

  @Query("SELECT * FROM pokemon WHERE `offset` BETWEEN :start AND :end")
  suspend fun getPokemonList(start: Int, end: Int): List<PokemonEntity>?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonList(pokemonList: List<PokemonEntity>)
}