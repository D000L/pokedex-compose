package com.doool.pokedex.domain.usecase

sealed class LoadState<out T : Any> {
  object Error : LoadState<Nothing>()
  object Loading : LoadState<Nothing>()
  data class Complete<T : Any>(val data: T) : LoadState<T>()
}