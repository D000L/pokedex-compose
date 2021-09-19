package com.doool.pokedex.module

import android.content.Context
import com.doool.pokedex.data.PokeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

  @Provides
  @Singleton
  fun provideRoomData(@ApplicationContext context: Context) = PokeDatabase.getDataBase(context)

  @Provides
  fun providePokemonDao(pokeDatabase: PokeDatabase) = pokeDatabase.pokemonDao()

  @Provides
  fun providePokemonDetailDao(pokeDatabase: PokeDatabase) = pokeDatabase.pokemonDetailDao()
}