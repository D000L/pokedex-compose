package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.repository.DownloadRepository
import javax.inject.Inject


class DownloadStaticData @Inject constructor(private val downloadRepository: DownloadRepository) :
  BaseUseCase<Unit>() {

  override suspend fun execute() {
    downloadRepository.downloadPokemonDetail()
    downloadRepository.downloadAllMove()
    downloadRepository.downloadAllItem()
  }
}

