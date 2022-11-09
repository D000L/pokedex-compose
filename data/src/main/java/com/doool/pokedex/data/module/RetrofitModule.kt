package com.doool.pokedex.data.module

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Qualifier
    internal annotation class PokeApiRetrofit

    @Qualifier
    internal annotation class NewsRetrofit

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
            .addConverterFactory(JsonConverter)
            .build()
    }

    @NewsRetrofit
    @Provides
    fun provideNewsRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.pokemon.com/us/api/")
            .addConverterFactory(JsonConverter)
            .build()
    }

    companion object {
        private val Json = Json {
            encodeDefaults = true
            isLenient = true
            ignoreUnknownKeys = true
            this.coerceInputValues = true
        }

        @OptIn(ExperimentalSerializationApi::class)
        private val JsonConverter = Json.asConverterFactory("application/json".toMediaType())
    }
}