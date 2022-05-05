package com.doool.pokedex.domain.usecase

import com.doool.pokedex.domain.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseParamsUseCase<PARAMS, RESULT> {

    abstract suspend fun execute(params: PARAMS): RESULT

    operator fun invoke(params: PARAMS): Flow<LoadState<RESULT>> = flow {
        emit(LoadState.Loading())
        emit(LoadState.Success(execute(params)))
    }.catch {
        emit(LoadState.Error(it))
    }.flowOn(Dispatchers.IO)
}

