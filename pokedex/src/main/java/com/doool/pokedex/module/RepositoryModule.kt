package com.doool.pokedex.module

import android.content.Context
import com.doool.pokedex.data.preference.SettingPreferences
import com.doool.pokedex.data.repository.DownloadRepositoryImpl
import com.doool.pokedex.data.repository.NewsRepositoryImpl
import com.doool.pokedex.data.repository.PokemonRepositoryImpl
import com.doool.pokedex.data.repository.SearchRepositoryImpl
import com.doool.pokedex.data.repository.SettingRepositoryImpl
import com.doool.pokedex.domain.repository.DownloadRepository
import com.doool.pokedex.domain.repository.NewsRepository
import com.doool.pokedex.domain.repository.PokemonRepository
import com.doool.pokedex.domain.repository.SearchRepository
import com.doool.pokedex.domain.repository.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository

    @Binds
    abstract fun bindDownloadRepository(impl: DownloadRepositoryImpl): DownloadRepository

    @Binds
    abstract fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository

    @Binds
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvideModule {

    @Provides
    fun bindSettingRepository(@ApplicationContext context: Context): SettingRepository {
        return SettingRepositoryImpl(SettingPreferences(context))
    }
}