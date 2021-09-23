package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.*
import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.PokemonEntity
import com.doool.pokedex.data.entity.PokemonSpeciesResponse
import com.doool.pokedex.data.entity.PokemonSpeciesEntity
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.data.toJson
import com.doool.pokedex.data.toModel
import com.doool.pokedex.domain.model.Pokemon
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
  private val pokeApiService: PokeApiService,
  private val pokemonDao: PokemonDao,
  private val pokemonDetailDao: PokemonDetailDao,
  private val pokemonSpeciesDao: PokemonSpeciesDao
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

  override suspend fun getPokemonDetail(id: Int): PokemonDetail {
    return fetchPokemonDetail(id).toModel()
  }

  private suspend fun fetchPokemonDetail(id: Int): PokemonDetailEntity {
    val localResult = pokemonDetailDao.getPokemonDetail(id)

    if (localResult == null) {
      val remoteResult = pokeApiService.getPokemon(id)
      pokemonDetailDao.insertPokemonDetail(remoteResult)
      return remoteResult
    }
    return localResult
  }

  override suspend fun getPokemonSpecies(id: Int): PokemonSpecies {
    return fetchPokemonSpecies(id).toModel()
  }

  private suspend fun fetchPokemonSpecies(id: Int): PokemonSpeciesResponse {
    val localResult = pokemonSpeciesDao.getPokemonSpecies(id)

    if (localResult == null) {
      val remoteResult = pokeApiService.getPokemonSpecies(id)
      val model = remoteResult.toJson()
      pokemonSpeciesDao.insertPokemonSpecies(PokemonSpeciesEntity(remoteResult.id,remoteResult.name,model))
      return remoteResult
    }
    return localResult.json.toModel()
  }
}