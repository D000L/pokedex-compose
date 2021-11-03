package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.repository.DownloadRepository
import javax.inject.Inject

class CheckIsDownloaded @Inject constructor(private val downloadRepository: DownloadRepository) :
  BaseUseCase<Boolean>() {

  override suspend fun execute() = !downloadRepository.shouldDownload()
}