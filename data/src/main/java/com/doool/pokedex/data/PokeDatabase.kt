package com.doool.pokedex.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.dao.SearchDao
import com.doool.pokedex.data.entity.AbilityEntity
import com.doool.pokedex.data.entity.FormEntity
import com.doool.pokedex.data.entity.ItemEntity
import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.PokemonEvolutionChainEntity
import com.doool.pokedex.data.entity.PokemonMoveEntity
import com.doool.pokedex.data.entity.PokemonSpeciesEntity
import com.doool.pokedex.data.entity.PokemonTypeResistanceEntity

@Database(
    entities = [PokemonDetailEntity::class,
        PokemonSpeciesEntity::class,
        PokemonEvolutionChainEntity::class,
        PokemonTypeResistanceEntity::class,
        PokemonMoveEntity::class,
        ItemEntity::class,
        AbilityEntity::class,
        FormEntity::class],
    version = 18,
    exportSchema = true
)
abstract class PokeDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "POKEDEX_DATABASE"

        fun getDataBase(context: Context): PokeDatabase =
            Room.databaseBuilder(context, PokeDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration().build()
    }

    abstract fun pokemonDetailDao(): PokemonDetailDao
    abstract fun pokemonSpeciesDao(): PokemonDao
    abstract fun pokemonSearchDao(): SearchDao
}