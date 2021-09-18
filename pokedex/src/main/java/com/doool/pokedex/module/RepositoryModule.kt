package com.doool.pokedex.module

import com.doool.pokedex.data.repository.PokemonRepositoryImpl
import com.doool.pokedex.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository
}