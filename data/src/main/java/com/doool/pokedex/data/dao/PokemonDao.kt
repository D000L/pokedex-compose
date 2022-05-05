package com.doool.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doool.pokedex.data.entity.AbilityEntity
import com.doool.pokedex.data.entity.FormEntity
import com.doool.pokedex.data.entity.ItemEntity
import com.doool.pokedex.data.entity.PokemonEvolutionChainEntity
import com.doool.pokedex.data.entity.PokemonMoveEntity
import com.doool.pokedex.data.entity.PokemonSpeciesEntity
import com.doool.pokedex.data.entity.PokemonTypeResistanceEntity

@Dao
interface PokemonDao {

    @Query("SELECT COUNT() FROM pokemon_move")
    suspend fun getMoveCount(): Int

    @Query("SELECT COUNT() FROM pokemon_detail")
    suspend fun getPokemonCount(): Int

    @Query("SELECT COUNT() FROM item")
    suspend fun getItemCount(): Int

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonMoveEntity(move: List<PokemonMoveEntity>)

    @Query("SELECT * FROM item WHERE name = :name")
    suspend fun getItemEntity(name: String): ItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemEntity(item: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemEntity(item: List<ItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbilityEntity(ability: AbilityEntity)

    @Query("SELECT * FROM ability WHERE name = :name")
    suspend fun getAbility(name: String): AbilityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormEntity(form: FormEntity)

    @Query("SELECT * FROM form WHERE name = :name")
    suspend fun getForm(name: String): FormEntity?
}
