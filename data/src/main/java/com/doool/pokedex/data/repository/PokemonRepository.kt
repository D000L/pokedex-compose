package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.dao.PokemonSpeciesDao
import com.doool.pokedex.data.entity.PokemonSpeciesEntity
import com.doool.pokedex.data.entity.PokemonSpeciesResponse
import com.doool.pokedex.data.mapper.toModel
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.data.toJson
import com.doool.pokedex.data.toModel
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
  private val pokeApiService: PokeApiService,
  private val pokemonDetailDao: PokemonDetailDao,
  private val pokemonSpeciesDao: PokemonSpeciesDao
) : PokemonRepository {

  override suspend fun downloadAllPokemon(startPage: Int, endPage: Int) {
    for (page in startPage..endPage) {
      val result = pokeApiService.getPokemon(page)
      pokemonDetailDao.insertPokemonDetail(result)
    }
  }

  override fun getAllPokemon(): Flow<List<PokemonDetail>> {
    return pokemonDetailDao.getAllPokemon().map {
      it.map { it.toModel() }
    }
  }

  override fun getPokemon(query: String): Flow<List<PokemonDetail>> {
    return pokemonDetailDao.getPokemon(query).map {
      it.map { it.toModel() }
    }
  }

  override suspend fun getPokemonSpecies(id: Int): PokemonSpecies {
    return fetchPokemonSpecies(id).toModel()
  }

  private suspend fun fetchPokemonSpecies(id: Int): PokemonSpeciesResponse {
    val localResult = pokemonSpeciesDao.getPokemonSpecies(id)

    if (localResult == null) {
      val remoteResult = pokeApiService.getPokemonSpecies(id)
      val model = remoteResult.toJson()
      pokemonSpeciesDao.insertPokemonSpecies(
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
}