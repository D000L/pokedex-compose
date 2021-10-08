package com.doool.pokedex.module

import com.doool.pokedex.data.service.NewsService
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.data.service.StaticApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

  @Provides
  fun providePokeApiService(@RetrofitModule.PokeApiRetrofit retrofit: Retrofit): PokeApiService = retrofit.create(
    PokeApiService::class.java
  )

  @Provides
  fun provideStaticApiService(@RetrofitModule.StaticApiRetrofit retrofit: Retrofit): StaticApiService = retrofit.create(
    StaticApiService::class.java
  )

  @Provides
  fun provideNewsService(@RetrofitModule.NewsRetrofit retrofit: Retrofit): NewsService = retrofit.create(
    NewsService::class.java
  )
}