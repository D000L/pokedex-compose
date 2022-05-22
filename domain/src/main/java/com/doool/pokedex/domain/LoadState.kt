package com.doool.pokedex.domain

sealed class LoadState<out T> {
    data class Error<T>(val e: Throwable? = null) : LoadState<T>()
    data class Loading<T>(val partialData: T? = null) : LoadState<T>()
    data class Success<T>(val data: T) : LoadState<T>()

    companion object {
        fun <T> loading(partialData: T? = null): LoadState<T> = Loading(partialData)
        fun <T> success(data: T): LoadState<T> = Success(data)
        fun <T> failure(e: Throwable?): LoadState<T> = Error(e)
    }
}

fun <T> LoadState<T>.getData() =
    (this as? LoadState.Success)?.data ?: (this as? LoadState.Loading)?.partialData

fun <T> LoadState<T>.isLoading() = this is LoadState.Loading
