package com.doool.pokedex.domain

import kotlinx.coroutines.flow.*

sealed class LoadState<out T> {
  object Error : LoadState<Nothing>()
  object Loading : LoadState<Nothing>()
  data class Success<T>(val data: T) : LoadState<T>()

  val isLoading: Boolean
    get() = this is LoadState.Loading
}

fun <T> Flow<T>.withLoadState() = flow {
  emit(LoadState.Loading)
  emitAll(this@withLoadState.map { LoadState.Success(it) })
}.catch {
  emit(LoadState.Error)
}