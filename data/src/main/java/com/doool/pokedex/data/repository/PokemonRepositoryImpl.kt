package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.PokemonEvolutionChainEntity
import com.doool.pokedex.data.entity.PokemonSpeciesEntity
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.response.PokemonDetailResponse
import com.doool.pokedex.data.response.PokemonEvolutionChainResponse
import com.doool.pokedex.data.response.PokemonSpeciesResponse
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.data.toJson
import com.doool.pokedex.data.toResponse
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonEvolutionChain
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
  private val pokeApiService: PokeApiService,
  private val pokemonDetailDao: PokemonDetailDao,
  private val pokemonDao: PokemonDao
) : PokemonRepository {

  override suspend fun getAllPokemon(): List<PokemonDetail> {
    return pokemonDetailDao.getAllPokemon().map {
      it.json.toResponse<PokemonDetailResponse>().toModel()
    }
  }

  override suspend fun searchPokemonList(query: String): List<PokemonDetail> {
    return pokemonDetailDao.searchPokemonList(query).map {
      it.json.toResponse<PokemonDetailResponse>().toModel()
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
    return localResult.json.toResponse()
  }

  override suspend fun getPokemonThumbnail(name: String): String {
    val pokemon = pokemonDetailDao.getPokemon(name).json.toResponse<PokemonDetailResponse>()
    return pokemon.sprites.frontDefault
  }
}