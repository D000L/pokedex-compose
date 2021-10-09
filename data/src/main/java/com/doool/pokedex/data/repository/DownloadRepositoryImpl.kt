package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.ItemEntity
import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.PokemonMoveEntity
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.data.service.StaticApiService
import com.doool.pokedex.data.toJson
import com.doool.pokedex.domain.repository.DownloadRepository
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
  private val staticApiService: StaticApiService,
  private val pokeApiService: PokeApiService,
  private val pokemonDetailDao: PokemonDetailDao,
  private val pokemonDao: PokemonDao
) : DownloadRepository {

  override suspend fun downloadPokemonDetail(page: Int) {
    val remoteResult = staticApiService.getPokemon(page)
    val entityList = remoteResult.map { pokemon ->
      PokemonDetailEntity(pokemon.id, pokemon.name, pokemon.toJson())
    }
    pokemonDetailDao.insertPokemonDetail(entityList)
  }

  override suspend fun downloadAllMove(){
    val remoteResult = pokeApiService.getAllPokemonMoveInfo()
    val moveList = remoteResult.results.map { move ->
      PokemonMoveEntity(name = move.name)
    }
    pokemonDao.insertPokemonMoveEntity(moveList)
  }

  override suspend fun downloadAllItem(){
    val remoteResult = pokeApiService.getAllItemInfo()
    val items = remoteResult.results.map { item ->
      ItemEntity(name = item.name)
    }
    pokemonDao.insertItemEntity(items)
  }
}