package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.repository.DownloadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed class LoadState<out T : Any> {
  object Error : LoadState<Nothing>()
  data class Loading(val amount: Float) : LoadState<Nothing>()
  data class Complete<T : Any>(val data: T) : LoadState<T>()
}

class DownloadStaticData @Inject constructor(private val downloadRepository: DownloadRepository) {

  operator fun invoke(): Flow<LoadState<Unit>> = flow {
    try {
      emit(LoadState.Loading(0f))
      for (page in 0..8) {
        downloadRepository.downloadPokemonDetail(page)
        emit(LoadState.Loading(page / 10f))
      }
      downloadRepository.downloadPokemonMove()
      emit(LoadState.Loading(0.9f))
      emit(LoadState.Complete(Unit))
    } catch (e: Throwable) {
      emit(LoadState.Error)
    }
  }
}

