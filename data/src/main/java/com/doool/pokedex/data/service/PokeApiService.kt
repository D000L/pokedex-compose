package com.doool.pokedex.data.service

import com.doool.pokedex.data.response.PokemonEvolutionChainResponse
import com.doool.pokedex.data.response.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

  @GET("pokemon-species/{id}")
  suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse

  @GET("evolution-chain/{id}")
  suspend fun getPokemonEvolutionChain(@Path("id") id: Int): PokemonEvolutionChainResponse
}