package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.repository.DownloadRepository
import javax.inject.Inject

class DownloadStaticData @Inject constructor(private val downloadRepository: DownloadRepository) {

  suspend operator fun invoke(): Result<Unit> {
    return try {
      downloadRepository.downloadStaticData(1, 2)

      Result.success(Unit)
    } catch (e: Throwable) {
      Result.failure(e)
    }
  }
}