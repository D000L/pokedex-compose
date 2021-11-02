package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.repository.DownloadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CheckIsDownloaded @Inject constructor(private val downloadRepository: DownloadRepository) {

  operator fun invoke(): Flow<Boolean> = flow {
    try {
      emit(!downloadRepository.shouldDownload())
    } catch (e: Throwable) {
      emit(false)
    }
  }.flowOn(Dispatchers.IO)
}