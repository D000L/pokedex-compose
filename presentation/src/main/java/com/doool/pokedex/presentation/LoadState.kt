package com.doool.pokedex.presentation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.*

sealed class LoadState<out T> {
  object Error : LoadState<Nothing>()
  object Loading : LoadState<Nothing>()
  data class Success<T>(val data: T) : LoadState<T>()
}

@Composable
fun <T : Any> LoadState<T>.Process(
  onError: @Composable () -> Unit = {},
  onLoading: @Composable () -> Unit = {},
  onComplete: @Composable (T) -> Unit = {}
) {
  when (this) {
    is LoadState.Success -> onComplete(data)
    LoadState.Error -> onError()
    is LoadState.Loading -> onLoading()
  }
}

fun <T> Flow<T>.withLoadState() = flow {
  emit(LoadState.Loading)
  emitAll(this@withLoadState.map { LoadState.Success(it) })
}.catch {
  emit(LoadState.Error)
}