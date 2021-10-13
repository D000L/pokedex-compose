package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.repository.DownloadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DownloadStaticData @Inject constructor(private val downloadRepository: DownloadRepository) {

  operator fun invoke(): Flow<LoadState<Unit>> = flow {
    try {
      emit(LoadState.Loading())
      downloadRepository.downloadPokemonDetail()
      downloadRepository.downloadAllMove()
      downloadRepository.downloadAllItem()
      emit(LoadState.Success(Unit))
    } catch (e: Throwable) {
      emit(LoadState.Error)
    }
  }
}

