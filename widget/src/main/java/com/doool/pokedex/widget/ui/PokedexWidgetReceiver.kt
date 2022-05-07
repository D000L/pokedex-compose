package com.doool.pokedex.widget.ui

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.CheckIsDownloaded
import com.doool.pokedex.domain.usecase.GetPokemon
import com.doool.pokedex.domain.usecase.GetPokemonSpecies
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

var widgetActions: WidgetActions? = null

@AndroidEntryPoint
class PokedexWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var checkIsDownloaded: CheckIsDownloaded

    @Inject
    lateinit var getPokemon: GetPokemon

    @Inject
    lateinit var getPokemonSpecies: GetPokemonSpecies

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.Default + job)

    override val glanceAppWidget = PokedexWidget(LoadState.loading(WidgetUIModel()))

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        widgetActions = WidgetActionsImpl(getPokemon, getPokemonSpecies)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        isDexDownloaded(context)
    }

    private fun isDexDownloaded(context: Context) {
        coroutineScope.launch {
            val ready = checkIsDownloaded.execute()

            context.getGlanceId()?.let { id ->
                updateAppWidgetState(context, id) { state ->
                    state[ReadyPrefKey] = ready
                }
                if (ready) widgetActions?.loadPokemon(context, 1)
            }
        }
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        job.cancel()
    }
}