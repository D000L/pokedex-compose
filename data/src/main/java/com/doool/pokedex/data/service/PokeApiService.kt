package com.doool.pokedex.data.service

import com.doool.pokedex.data.entity.ListItem
import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.PokemonEntity
import com.doool.pokedex.data.entity.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

  @GET("pokemon")
  suspend fun getPokemonList(
    @Query("offset") offset: Int = 0,
    @Query("limit") limit: Int = 20
  ): ListItem<PokemonEntity>

  @GET("pokemon/{id}")
  suspend fun getPokemon(@Path("id") id: Int): PokemonDetailEntity

  @GET("pokemon-species/{id}")
  suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse
}