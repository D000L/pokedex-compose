package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.repository.DownloadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DownloadStaticData @Inject constructor(private val downloadRepository: DownloadRepository) {

  operator fun invoke(): Flow<Unit> = flow {
    downloadRepository.downloadPokemonDetail()
    downloadRepository.downloadAllMove()
    downloadRepository.downloadAllItem()
    emit(Unit)
  }
}

