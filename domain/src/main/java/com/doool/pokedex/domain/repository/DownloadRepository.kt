package com.doool.pokedex.domain.repository

interface DownloadRepository {

  suspend fun downloadPokemonDetail()
  suspend fun downloadAllMove()
  suspend fun downloadAllItem()
}