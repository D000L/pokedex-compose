package com.doool.pokedex.data.service

import com.doool.pokedex.data.response.*
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

  @GET("pokemon-species/{id}")
  suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse

  @GET("evolution-chain/{id}")
  suspend fun getPokemonEvolutionChain(@Path("id") id: Int): PokemonEvolutionChainResponse

  @GET("type/{name}")
  suspend fun getPokemonTypeResistance(@Path("name") name: String): PokemonTypeResistanceResponse

  @GET("move/{name}/")
  suspend fun getPokemonMove(@Path("name") name: String): PokemonMoveResponse

  @GET("move?offset=0&limit=999")
  suspend fun getAllPokemonMoveInfo(): ListItem<InfoResponse>
}