package com.doool.pokedex.module

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Qualifier
    internal annotation class PokeApiRetrofit

    @Qualifier
    internal annotation class StaticApiRetrofit

    @Provides
    fun provideOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor { message ->
            Log.i("API CALL : ", message + "")
        }

        val logger = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addNetworkInterceptor(logger)
            .build()
    }

    @PokeApiRetrofit
    @Provides
    fun providePokeApiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @StaticApiRetrofit
    @Provides
    fun provideStaticApiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://raw.githubusercontent.com/D000L/pokedex-compose/main/staticApi/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}