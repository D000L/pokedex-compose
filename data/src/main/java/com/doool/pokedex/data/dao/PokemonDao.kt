package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doool.pokedex.data.entity.*
import androidx.lifecycle.LiveData




@Dao
interface PokemonDao {

  @Query("SELECT COUNT() FROM pokemon_move")
  suspend fun getMoveCount(): Int

  @Query("SELECT COUNT() FROM pokemon_detail")
  suspend fun getPokemonCount(): Int

  @Query("SELECT COUNT() FROM item")
  suspend fun getItemCount(): Int

  @Query("SELECT * FROM pokemon_species WHERE name = :name")
  suspend fun getPokemonSpecies(name: String): PokemonSpeciesEntity?

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
  suspend fun getPokemonMoveEntity(name: String): PokemonMoveEntity

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonMoveEntity(move: PokemonMoveEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPokemonMoveEntity(move: List<PokemonMoveEntity>)

  @Query("SELECT * FROM item WHERE name = :name")
  suspend fun getItemEntity(name: String): ItemEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertItemEntity(item: ItemEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertItemEntity(item: List<ItemEntity>)
}
