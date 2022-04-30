package com.doool.pokedex.widget.ui

import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import com.doool.pokedex.domain.usecase.CheckIsDownloaded
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PokedexWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var checkIsDownloaded: CheckIsDownloaded

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    override val glanceAppWidget = PokedexWidget()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
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
            val ready = !checkIsDownloaded.execute()
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