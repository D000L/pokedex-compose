package com.doool.pokedex.data.service

import com.doool.pokedex.data.entity.PokemonDetailEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface StaticApiService {

  @GET("pokedex/{page}.json")
  suspend fun getPokemon(@Path("page") page: Int): List<PokemonDetailEntity>
}