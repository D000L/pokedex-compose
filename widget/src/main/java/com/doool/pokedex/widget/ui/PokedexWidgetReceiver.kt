package com.doool.pokedex.widget.ui

import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.CheckIsDownloaded
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

interface WidgetLoader {
    suspend fun nextPokemon(context: Context, id: Int)
    suspend fun prevPokemon(context: Context, id: Int)
    suspend fun updatePokemonDetail(context: Context, id: Int)
}

fun <T1, T2> combineLoadState(
    flow: Flow<LoadState<T1>>,
    flow2: Flow<LoadState<T2>>,
): Flow<LoadState<Pair<T1, T2>>> = combine(flow, flow2) { data1, data2 ->
    if (data1 is LoadState.Success && data2 is LoadState.Success) {
        LoadState.success(Pair(data1.data, data2.data))
    } else if (data1 is LoadState.Error || data2 is LoadState.Error) {
        val error = (data1 as? LoadState.Error)?.e ?: (data2 as? LoadState.Error)?.e
        LoadState.failure(error)
    } else {
        LoadState.loading()
    }
}


class WidgetLoaderImpl(
    private val getPokemon: GetPokemon,
    private val getPokemonSpecies: GetPokemonSpecies,
) : WidgetLoader {
    override suspend fun nextPokemon(context: Context, id: Int) {
        updatePokemonDetail(context, id + 1)
    }

    override suspend fun prevPokemon(context: Context, id: Int) {
        updatePokemonDetail(context, id - 1)
    }

    private suspend fun recomposable(context: Context, state: WidgetState) {
        GlanceAppWidgetManager(context)
            .getGlanceIds(PokedexWidget::class.java)
            .forEach { id ->
                PokedexWidget(state).update(context, id)
            }
    }

    suspend fun <T> Flow<LoadState<T>>.collectSuccess(
        context: Context,
        onSuccess: suspend (T) -> Unit,
    ) {
        collectLatest {
            when (it) {
                is LoadState.Error -> {
                }
                is LoadState.Loading -> recomposable(context, WidgetState.Loading)
                is LoadState.Success -> onSuccess(it.data)
            }
        }
    }

    override suspend fun updatePokemonDetail(context: Context, id: Int) {
        getPokemon(GetPokemon.Params.ById(id)).collectSuccess(context) { pokemon ->
            getPokemonSpecies(pokemon.species.id).collectSuccess(context) { species ->
                recomposable(context, WidgetState.Success(
                    id,
                    pokemon.name,
                    pokemon.weight,
                    pokemon.height,
                    species.genera[0].text
                ))
            }
        }
    }
}

var widgetLoader: WidgetLoader? = null

@AndroidEntryPoint
class PokedexWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var checkIsDownloaded: CheckIsDownloaded

    @Inject
    lateinit var getPokemon: GetPokemon

    @Inject
    lateinit var getPokemonSpecies: GetPokemonSpecies

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    override val glanceAppWidget = PokedexWidget(WidgetState.Success(1))


    override fun onEnabled(context: Context?) {
        widgetLoader = WidgetLoaderImpl(getPokemon, getPokemonSpecies)
        super.onEnabled(context)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        coroutineScope.launch {
            observeData(context)


        }
    }

    private suspend fun observeData(context: Context) {
        val glanceId =
            GlanceAppWidgetManager(context).getGlanceIds(PokedexWidget::class.java).firstOrNull()
        glanceId?.let { id ->
            val ready = checkIsDownloaded.execute()
            Log.d("asdgasdg", ready.toString())
            updateAppWidgetState(context, id) { state ->
                state[ReadyKey] = ready
            }
            Log.d("asdgasdg", "updateAppWidgetState")
//            glanceAppWidget.update(context, glanceId)
            Log.d("asdgasdg", "update")
        }
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        job.cancel()
    }
}