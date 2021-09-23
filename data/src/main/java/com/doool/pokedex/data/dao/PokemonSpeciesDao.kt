package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doool.pokedex.data.entity.PokemonSpeciesEntity

@Dao
interface PokemonSpeciesDao {

  @Query("SELECT * FROM pokemon_species WHERE id = :id")
  suspend fun getPokemonSpecies(id: Int): PokemonSpeciesEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonSpecies(species: PokemonSpeciesEntity)
}
