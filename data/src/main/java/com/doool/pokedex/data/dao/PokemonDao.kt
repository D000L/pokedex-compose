package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doool.pokedex.data.entity.PokemonEvolutionChainEntity
import com.doool.pokedex.data.entity.PokemonEvolutionChainResponse
import com.doool.pokedex.data.entity.PokemonSpeciesEntity

@Dao
interface PokemonDao {

  @Query("SELECT * FROM pokemon_species WHERE id = :id")
  suspend fun getPokemonSpecies(id: Int): PokemonSpeciesEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonSpecies(species: PokemonSpeciesEntity)

  @Query("SELECT * FROM pokemon_evolution_chain WHERE id = :id")
  suspend fun getPokemonEvolutionChain(id: Int): PokemonEvolutionChainEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonEvolutionChain(species: PokemonEvolutionChainEntity)
}
