package com.doool.pokedex.data.repository

import androidx.paging.DataSource
import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.PokemonEvolutionChainEntity
import com.doool.pokedex.data.entity.PokemonSpeciesEntity
import com.doool.pokedex.data.entity.PokemonTypeResistanceEntity
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.response.PokemonDetailResponse
import com.doool.pokedex.data.response.PokemonEvolutionChainResponse
import com.doool.pokedex.data.response.PokemonSpeciesResponse
import com.doool.pokedex.data.response.PokemonTypeResistanceResponse
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.data.toJson
import com.doool.pokedex.data.toResponse
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.model.PokemonTypeResistance
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
  private val pokeApiService: PokeApiService,
  private val pokemonDetailDao: PokemonDetailDao,
  private val pokemonDao: PokemonDao
) : PokemonRepository {

  override fun searchPokemonList(query: String?): DataSource.Factory<Int, PokemonDetail> {
    val dataSourceFactory =
      if (query.isNullOrEmpty()) pokemonDetailDao.getAllPokemon()
      else pokemonDetailDao.searchPokemonList(query)

    return dataSourceFactory.mapByPage {
      it.map { it.json.toResponse<PokemonDetailResponse>().toModel() }
    }
  }

  override suspend fun getPokemon(id: Int): PokemonDetail {
    return pokemonDetailDao.getPokemon(id).json.toResponse<PokemonDetailResponse>().toModel()
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
    val pokemon = pokemonDetailDao.getPokemon(name).json.toResponse<PokemonDetailResponse>()
    return pokemon.sprites.other.artwork.frontDefault
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
}