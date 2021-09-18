package com.doool.pokedex.data.service

import com.doool.pokedex.data.entity.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

  @GET("/api/v2/pokemon/{id}")
  suspend fun getPokemon(@Path("id") id: Int): Pokemon
}