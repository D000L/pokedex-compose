package com.doool.pokedex.domain.repository

interface DownloadRepository {

  suspend fun downloadStaticData(startPage: Int, endPage: Int)
}