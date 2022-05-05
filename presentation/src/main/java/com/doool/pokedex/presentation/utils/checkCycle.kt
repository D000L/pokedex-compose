package com.doool.pokedex.presentation.utils

import android.util.Log
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive

fun <T : Any> Flow<T>.flowCycleLogging(log: String) = this.onStart {
    Log.d("CheckCycle", "$log Start")
}.onEach {
    Log.d("CheckCycle", "$log Each ${it.toString().take(80)}")
}.onCompletion {
    Log.d("CheckCycle", "$log Completion ${currentCoroutineContext().isActive}")
}.catch {
    Log.d("CheckCycle", "$log Catch")
}