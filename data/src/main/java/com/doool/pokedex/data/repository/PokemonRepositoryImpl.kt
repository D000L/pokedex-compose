package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.*
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.response.PokemonDetailResponse
import com.doool.pokedex.data.response.PokemonEvolutionChainResponse
import com.doool.pokedex.data.response.PokemonSpeciesResponse
import com.doool.pokedex.data.response.PokemonTypeResistanceResponse
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.data.toJson
import com.doool.pokedex.data.toResponse
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
  private val pokeApiService: PokeApiService,
  private val pokemonDetailDao: PokemonDetailDao,
  private val pokemonDao: PokemonDao
) : PokemonRepository {

  override suspend fun getPokemon(name: String): PokemonDetail {
    return pokemonDetailDao.getPokemon(name).toModel()
  }

  override suspend fun getPokemonSpecies(name: String): PokemonSpecies {
    return fetchPokemonSpecies(name).toModel()
  }

  private suspend fun fetchPokemonSpecies(name: String): PokemonSpeciesResponse {
    val localResult = pokemonDao.getPokemonSpecies(name)

    if (localResult == null) {
      val remoteResult = pokeApiService.getPokemonSpecies(name)
      val model = remoteResult.toJson()
      pokemonDao.insertPokemonSpecies(
        PokemonSpeciesEntity(
          remoteResult.name,
          remoteResult.id,
          model
        )
      )
      return remoteResult
    }
    return localResult.json.toResponse()
  }

  override suspend fun getPokemonEvolutionChain(url: String): List<PokemonEvolutionChain> {
    return fetchPokemonEvolutionChain(parseEvolutionChainId(url)).toModel()
  }

  private fun parseEvolutionChainId(url: String): Int {
    return url.removePrefix("https://pokeapi.co/api/v2/evolution-chain/").removeSuffix("/")
      .toInt()
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
    return localResult.json.toResponse()
  }

  override suspend fun getPokemonThumbnail(name: String): String {
    val pokemon = pokemonDetailDao.getPokemon(name)?.json?.toResponse<PokemonDetailResponse>()
    return pokemon?.sprites?.other?.artwork?.frontDefault ?: ""
  }

  override suspend fun getPokemonTypeResistance(name: String): PokemonTypeResistance {
    return fetchPokemonTypeResistance(name).toModel()
  }

  private suspend fun fetchPokemonTypeResistance(name: String): PokemonTypeResistanceResponse {
    val localResult = pokemonDao.getPokemonTypeResistanceEntity(name)

    if (localResult == null) {
      val remoteResult = pokeApiService.getPokemonTypeResistance(name)
      val model = remoteResult.toJson()
      pokemonDao.insertPokemonTypeResistanceEntity(
        PokemonTypeResistanceEntity(
          remoteResult.id,
          remoteResult.name,
          model
        )
      )
      return remoteResult
    }
    return localResult.json.toResponse()
  }

  override suspend fun getPokemonMove(name: String): PokemonMove {
    return pokemonDao.getPokemonMoveEntity(name).toModel()
  }

  override suspend fun fetchMove(names: List<String>) {
    val results = names.map {
      val remoteResult = pokeApiService.getPokemonMove(it)
      val model = remoteResult.toJson()
      PokemonMoveEntity(
        remoteResult.name,
        remoteResult.id,
        model
      )
    }
    pokemonDao.insertPokemonMoveEntity(results)
  }

  override suspend fun fetchItem(names: List<String>) {
    val results = names.map {
      val remoteResult = pokeApiService.getItem(it)
      val model = remoteResult.toJson()
      ItemEntity(
        remoteResult.name,
        remoteResult.id,
        model
      )
    }
    pokemonDao.insertItemEntity(results)
  }

  override suspend fun fetchPokemon(names: List<String>) {
    val results = names.map {
      val remoteResult = pokeApiService.getPokemon(it)
      val model = remoteResult.toJson()
      PokemonDetailEntity(
        remoteResult.name,
        remoteResult.id,
        remoteResult.id,
        model
      )
    }
    pokemonDetailDao.insertPokemonDetail(results)
  }
}