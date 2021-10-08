package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doool.pokedex.data.entity.PokemonEvolutionChainEntity
import com.doool.pokedex.data.entity.PokemonMoveEntity
import com.doool.pokedex.data.entity.PokemonSpeciesEntity
import com.doool.pokedex.data.entity.PokemonTypeResistanceEntity

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

  @Query("SELECT * FROM pokemon_type_resistance WHERE name = :name")
  suspend fun getPokemonTypeResistanceEntity(name: String): PokemonTypeResistanceEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonTypeResistanceEntity(typeResistance: PokemonTypeResistanceEntity)

  @Query("SELECT * FROM pokemon_move WHERE name = :name")
  suspend fun getPokemonMoveEntity(name: String): PokemonMoveEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonMoveEntity(move: PokemonMoveEntity)
}
