package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.*
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.data.service.StaticApiService
import com.doool.pokedex.data.toJson
import com.doool.pokedex.data.toModel
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
  private val pokeApiService: PokeApiService,
  private val staticApiService: StaticApiService,
  private val pokemonDetailDao: PokemonDetailDao,
  private val pokemonDao: PokemonDao
) : PokemonRepository {

  override suspend fun downloadAllPokemon(startPage: Int, endPage: Int) {
    for (page in startPage..endPage) {
      val result = staticApiService.getPokemon(page)
      pokemonDetailDao.insertPokemonDetail(result)
    }
  }

  override fun getAllPokemon(): Flow<List<PokemonDetail>> {
    return pokemonDetailDao.getAllPokemon().map {
      it.map { it.toModel() }
    }
  }

  override fun getPokemon(query: String): Flow<List<PokemonDetail>> {
    return pokemonDetailDao.getPokemonList(query).map {
      it.map { it.toModel() }
    }
  }

  override suspend fun getPokemon(id: Int): PokemonDetail {
    return pokemonDetailDao.getPokemon(id).toModel()
  }

  override suspend fun getPokemonSpecies(id: Int): PokemonSpecies {
    return fetchPokemonSpecies(id).toModel()
  }

  private suspend fun fetchPokemonSpecies(id: Int): PokemonSpeciesResponse {
    val localResult = pokemonDao.getPokemonSpecies(id)

    if (localResult == null) {
      val remoteResult = pokeApiService.getPokemonSpecies(id)
      val model = remoteResult.toJson()
      pokemonDao.insertPokemonSpecies(
        PokemonSpeciesEntity(
          remoteResult.id,
          remoteResult.name,
          model
        )
      )
      return remoteResult
    }
    return localResult.json.toModel()
  }

  override suspend fun getPokemonEvolutionChain(id: Int): List<PokemonEvolutionChain> {
    return fetchPokemonEvolutionChain(id).toModel()
  }

  private suspend fun fetchPokemonEvolutionChain(id: Int): PokemonEvolutionChainResponse {
    val localResult = pokemonDao.getPokemonEvolutionChain(id)

    if (localResult == null) {
      val remoteResult = pokeApiService.getPokemonEvolutionChain(id)
      val model = remoteResult.toJson()
      pokemonDao.insertPokemonEvolutionChain(
        PokemonEvolutionChainEntity(
          remoteResult.id,
          model
        )
      )
      return remoteResult
    }
    return localResult.json.toModel()
  }

  override suspend fun getSprites(name: String): String {
    return pokemonDetailDao.getPokemon(name).sprites.frontDefault
  }
}