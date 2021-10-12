package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDao
import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.ItemEntity
import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.entity.PokemonMoveEntity
import com.doool.pokedex.data.service.PokeApiService
import com.doool.pokedex.domain.repository.DownloadRepository
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
  private val pokeApiService: PokeApiService,
  private val pokemonDetailDao: PokemonDetailDao,
  private val pokemonDao: PokemonDao
) : DownloadRepository {

  override suspend fun shouldDownload(): Boolean {
    return pokemonDao.getPokemonCount() == 0 || pokemonDao.getItemCount() == 0 || pokemonDao.getMoveCount() == 0
  }

  override suspend fun downloadPokemonDetail() {
    val remoteResult = pokeApiService.getAllPokemonInfo()
    val entityList = remoteResult.results.mapIndexed { index, pokemon ->
      PokemonDetailEntity(name = pokemon.name, index = index + 1)
    }
    pokemonDetailDao.insertPokemonDetail(entityList)
  }

  override suspend fun downloadAllMove() {
    val remoteResult = pokeApiService.getAllPokemonMoveInfo()
    val moveList = remoteResult.results.mapIndexed { index, move ->
      PokemonMoveEntity(name = move.name, index = index + 1)
    }
    pokemonDao.insertPokemonMoveEntity(moveList)
  }

  override suspend fun downloadAllItem() {
    val remoteResult = pokeApiService.getAllItemInfo()
    val items = remoteResult.results.map { item ->
      ItemEntity(name = item.name)
    }
    pokemonDao.insertItemEntity(items)
  }
}