package com.doool.pokedex.data.service

import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

  @GET("pokedex/{page}.json")
  suspend fun getPokemon(@Path("page") page: Int): List<PokemonDetailEntity>

  @GET("pokemon-species/{id}")
  suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse
}