package com.doool.pokedex.data.service

import com.doool.pokedex.data.entity.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

  @GET("pokemon-species/{id}")
  suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse
}