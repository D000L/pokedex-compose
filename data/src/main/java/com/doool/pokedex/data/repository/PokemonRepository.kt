package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.PokemonEntity
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
  private val pokeApiService: PokeApiService,
  private val pokemonDao: PokemonDao,
  private val pokemonDetailDao: PokemonDetailDao
) : PokemonRepository {

  override suspend fun getPokemonList(offset: Int, countPerPage: Int): List<Pokemon> {
    return fetchPokemonList(offset, countPerPage).map { it.toModel() }
  }

  private suspend fun fetchPokemonList(offset: Int, countPerPage: Int): List<PokemonEntity> {
    val localResult = pokemonDao.getPokemonList(offset, offset + countPerPage)

    if (localResult.isNullOrEmpty()) {
      val remoteResult = pokeApiService.getPokemonList(offset, countPerPage).results
      remoteResult.forEachIndexed { index, pokemon ->
        pokemon.offset = offset + index
      }
      pokemonDao.insertPokemonList(remoteResult)
      return remoteResult
    }
    return localResult
  }

  override suspend fun getPokemonDetail(name: String): PokemonDetail {
    return fetchPokemonDetail(name).toModel()
  }

  private suspend fun fetchPokemonDetail(name: String): PokemonDetailEntity {
    val localResult = pokemonDetailDao.getPokemonDetail(name)

    if (localResult == null) {
      val remoteResult = pokeApiService.getPokemon(name)
      pokemonDetailDao.insertPokemonDetail(remoteResult)
      return remoteResult
    }
    return localResult
  }
}