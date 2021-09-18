package com.doool.pokedex.data.repository

import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val pokeApiService: PokeApiService) : PokemonRepository {

  override suspend fun getPokemon(id: Int): Pokemon {
    return pokeApiService.getPokemon(id).toModel()
  }

}