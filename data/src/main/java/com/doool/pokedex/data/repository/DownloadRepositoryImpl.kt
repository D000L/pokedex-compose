package com.doool.pokedex.data.repository

import com.doool.pokedex.data.dao.PokemonDetailDao
import com.doool.pokedex.data.entity.PokemonDetailEntity
import com.doool.pokedex.data.service.StaticApiService
import com.doool.pokedex.data.toJson
import com.doool.pokedex.domain.repository.DownloadRepository
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
  private val staticApiService: StaticApiService,
  private val pokemonDetailDao: PokemonDetailDao,
) : DownloadRepository {

  override suspend fun downloadStaticData(startPage: Int, endPage: Int) {
    for (page in startPage..endPage) {
      downloadPokemonDetail(page)
    }
  }

  private suspend fun downloadPokemonDetail(page: Int) {
    val remoteResult = staticApiService.getPokemon(page)
    val entityList = remoteResult.map { pokemon ->
      PokemonDetailEntity(pokemon.id, pokemon.name, pokemon.toJson())
    }
    pokemonDetailDao.insertPokemonDetail(entityList)
  }
}