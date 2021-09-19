package com.doool.pokedex.data.repository

import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val pokeApiService: PokeApiService) : PokemonRepository {

  override suspend fun getPokemonList(offset: Int, countPerPage: Int): List<Pokemon> {
    return pokeApiService.getPokemonList(offset, countPerPage).results.map { it.toModel() }
  }

  override suspend fun getPokemonDetail(name: String): PokemonDetail {
    return pokeApiService.getPokemon(name).toModel()
  }

}