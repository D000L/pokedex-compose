package com.doool.pokedex.domain.repository

interface DownloadRepository {

  suspend fun downloadPokemonDetail(page: Int)
  suspend fun downloadPokemonMove()
}