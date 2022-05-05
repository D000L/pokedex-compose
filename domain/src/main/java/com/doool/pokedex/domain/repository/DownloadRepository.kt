package com.doool.pokedex.domain.repository

interface DownloadRepository {

    suspend fun shouldDownload(): Boolean

    suspend fun downloadPokemonDetail()
    suspend fun downloadAllMove()
    suspend fun downloadAllItem()
}