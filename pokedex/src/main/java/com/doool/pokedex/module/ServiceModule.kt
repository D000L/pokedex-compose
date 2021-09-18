package com.doool.pokedex.module

import com.doool.pokedex.data.service.PokeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

  @Provides
  fun providePokeApiService(retrofit: Retrofit): PokeApiService = retrofit.create(
    PokeApiService::class.java
  )
}