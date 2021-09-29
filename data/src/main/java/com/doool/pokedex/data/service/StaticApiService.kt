package com.doool.pokedex.data.service

import com.doool.pokedex.data.response.PokemonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StaticApiService {

  @GET("pokemon/{page}.json")
  suspend fun getPokemon(@Path("page") page: Int): List<PokemonDetailResponse>
}