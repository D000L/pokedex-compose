package com.doool.pokedex.widget.ui

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.*
import androidx.glance.text.Text
import coil.Coil
import coil.executeBlocking
import coil.request.ImageRequest
import com.doool.pokedex.widget.R

fun getImageUrl(id: Int) =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

val IndexKey = intPreferencesKey("Index")
val ReadyKey = booleanPreferencesKey("ready")

class PokedexWidget() : GlanceAppWidget() {

    @Composable
    override fun Content() {
        PokeDex()
    }

    @Composable
    private fun PokeDex() {
        val context = LocalContext.current
        val index = currentState(IndexKey) ?: 1
        val ready = currentState(ReadyKey) ?: true

        val image = (Coil.imageLoader(context).executeBlocking(
            ImageRequest.Builder(context).data(getImageUrl(index)).build()
        ).drawable as? BitmapDrawable)?.bitmap

//        modifier = GlanceModifier.background(Color.Black).padding(80.dp).background(Color.White)

        Box(modifier = GlanceModifier.background(Color.Black).padding(6.dp).cornerRadius(40.dp)) {
            Box(modifier = GlanceModifier.background(Color.White).padding(6.dp).cornerRadius(40.dp)) {
                Row(
                    modifier = GlanceModifier.background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ImageButton<MovePrevAction>(R.drawable.ic_left_arrow)

                    if(!ready) {
                        image?.let {
                            Box(GlanceModifier.defaultWeight()) {
                                Text(text = index.toString())
                                Image(
                                    modifier = GlanceModifier.fillMaxSize().padding(4.dp),
                                    provider = ImageProvider(it),
                                    contentDescription = "Pokemon"
                                )
                            }
                        }
                    }else {

                            Box(GlanceModifier.defaultWeight()) {
                                Text(text = index.toString())

                            }

                    }

                    ImageButton<MoveNextAction>(R.drawable.ic_right_arrow)
                }
            }
        }
    }

    @Composable
    private inline fun <reified T : ActionCallback> ImageButton(@DrawableRes imageRes: Int) {
        Image(
            modifier = GlanceModifier.padding(4.dp).size(24.dp)
                .clickable(onClick = actionRunCallback<T>()),
            provider = ImageProvider(imageRes),
            contentDescription = "Pokemon"
        )
    }
}

class MovePrevAction : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { state ->
            state[IndexKey] = (state[IndexKey] ?: 1).minus(1).coerceAtLeast(1)
        }
        PokedexWidget().update(context, glanceId)
    }
}

class MoveNextAction : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { state ->
            state[IndexKey] = (state[IndexKey] ?: 1).plus(1)
        }
        PokedexWidget().update(context, glanceId)
    }
}


