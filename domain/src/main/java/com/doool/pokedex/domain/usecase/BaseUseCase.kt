package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<RESULT> {

    abstract suspend fun execute(): RESULT

    operator fun invoke(): Flow<LoadState<RESULT>> = flow {
        emit(LoadState.Loading())
        emit(LoadState.Success(execute()))
    }.catch {
        emit(LoadState.Error(it))
    }.flowOn(Dispatchers.IO)
}