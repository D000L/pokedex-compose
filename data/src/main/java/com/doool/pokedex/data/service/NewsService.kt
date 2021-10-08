package com.doool.pokedex.data.service

import com.doool.pokedex.data.response.PokemonNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

  @GET("news")
  suspend fun getNewsList(@Query("index") index: Int, @Query("count") count: Int): List<PokemonNewsResponse>
}