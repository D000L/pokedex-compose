package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.*
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.response.*
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
    return fetchPokemon(name).toModel()
  }

  private suspend fun fetchPokemon(name: String): PokemonDetailResponse {
    val localResult = pokemonDetailDao.getPokemon(name)

    if (localResult.json == null) {
      val remoteResult = pokeApiService.getPokemon(name)
      pokemonDetailDao.insertPokemonDetail(
        PokemonDetailEntity(
          remoteResult.name,
          remoteResult.id,
          remoteResult.toJson()
        )
      )
      return remoteResult
    }
    return localResult.json.toResponse()
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
    return fetchMove(name).toModel()
  }

  private suspend fun fetchMove(names: String): PokemonMoveResponse {
    val localResult = pokemonDao.getPokemonMoveEntity(names)

    if (localResult?.json == null) {
      val remoteResult = pokeApiService.getPokemonMove(names)
      pokemonDao.insertPokemonMoveEntity(
        PokemonMoveEntity(
          remoteResult.name,
          remoteResult.id,
          remoteResult.toJson()
        )
      )
      return remoteResult
    }
    return localResult.json.toResponse()
  }

  override suspend fun getItem(name: String): Item {
    return fetchItem(name).toModel()
  }

  private suspend fun fetchItem(names: String): ItemResponse {
    val localResult = pokemonDao.getItemEntity(names)

    if (localResult?.json == null) {
      val remoteResult = pokeApiService.getItem(names)
      pokemonDao.insertItemEntity(
        ItemEntity(
          remoteResult.name,
          remoteResult.id,
          remoteResult.toJson()
        )
      )
      return remoteResult
    }
    return localResult.json.toResponse()
  }

  override suspend fun getAbility(name: String): Ability {
    return fetchAbility(name).toModel()
  }

  private suspend fun fetchAbility(names: String): AbilityResponse {
    val localResult = pokemonDao.getAbility(names)

    if (localResult?.json == null) {
      val remoteResult = pokeApiService.getAbility(names)
      pokemonDao.insertAbilityEntity(
        AbilityEntity(
          remoteResult.name,
          remoteResult.id,
          remoteResult.toJson()
        )
      )
      return remoteResult
    }
    return localResult.json.toResponse()
  }
}