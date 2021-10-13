package com.doool.pokedex.domain

sealed class LoadState<out T> {
  object Error : LoadState<Nothing>()
  data class Loading(val placeholderCount: Int = 0) : LoadState<Nothing>()
  data class Success<T>(val data: T) : LoadState<T>()
}