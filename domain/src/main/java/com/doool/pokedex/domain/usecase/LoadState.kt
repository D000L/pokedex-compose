package com.doool.pokedex.domain.usecase

sealed class LoadState<out T> {
  object Error : LoadState<Nothing>()
  object Loading : LoadState<Nothing>()
  data class Success<T>(val data: T) : LoadState<T>()
}