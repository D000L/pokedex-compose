package com.doool.pokedex.widget.ui

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import coil.Coil
import coil.executeBlocking
import coil.request.ImageRequest
import com.doool.pokedex.widget.R

fun getImageUrl(id: Int) =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

val IndexKey = intPreferencesKey("Index")
val ReadyKey = booleanPreferencesKey("ready")

sealed class WidgetState {
    object Loading : WidgetState()
    data class Success(
        val id: Int = 1,
        val name: String = "",
        val height: Int = 0,
        val weight: Int = 0,
        val species: String = "",
    ) : WidgetState()
}

class PokedexWidget(val state: WidgetState) : GlanceAppWidget() {


    override val sizeMode = SizeMode.Exact

    @Composable
    override fun Content() {
        PokeDexBig()
    }

    @Composable
    private fun PokeDexBig() {
        val context = LocalContext.current
        val index = (state as? WidgetState.Success)?.id ?: 1
        val ready = currentState(ReadyKey) ?: true

        val image = (Coil.imageLoader(context).executeBlocking(
            ImageRequest.Builder(context).data(getImageUrl(index)).build()
        ).drawable as? BitmapDrawable)?.bitmap

//        modifier = GlanceModifier.background(Color.Black).padding(80.dp).background(Color.White)

        Column(GlanceModifier.fillMaxSize().background(ColorProvider(R.color.background_red))
            .padding(20.dp)) {
            Row(GlanceModifier.defaultWeight().fillMaxWidth()
                .background(ImageProvider(R.drawable.background)).padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically) {
                image?.let {
                    Image(
                        modifier = GlanceModifier.defaultWeight().size(100.dp)
                            .padding(4.dp),
                        provider = ImageProvider(it),
                        contentDescription = "Pokemon"
                    )
                } ?: run {
                    Box(modifier = GlanceModifier.defaultWeight().size(120.dp)
                        .padding(4.dp)) {}
                }

                Column(GlanceModifier.defaultWeight()) {
                    when (state) {
                        WidgetState.Loading -> {

                        }
                        is WidgetState.Success -> {
                            Text("#%03d".format(index))
                            Text(state.species)
                            Text("Height")
                            Text("%.1f m".format(state.height / 10f))
                            Text("Weight")
                            Text("%.1f kg".format(state.weight / 10f))
                        }
                    }
                }
            }
            Spacer(GlanceModifier.width(20.dp))
            Row(GlanceModifier.height(90.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Box(GlanceModifier.height(90.dp).defaultWeight()
                    .background(Color.Black)) {}
                Spacer(GlanceModifier.width(20.dp))
                Row(GlanceModifier.size(90.dp).background(ImageProvider(R.drawable.keypad)),
                    verticalAlignment = Alignment.CenterVertically) {
                    Button(text = "",
                        modifier = GlanceModifier.size(30.dp).background(Color.Transparent),
                        onClick = actionRunCallback<MovePrevAction>(
                            actionParametersOf(
                                ActionParameters.Key<Int>("Index") to index
                            )))
                    Box(GlanceModifier.size(30.dp, 90.dp)) {}
                    Button(text = "",
                        modifier = GlanceModifier.size(30.dp).background(Color.Transparent),
                        onClick = actionRunCallback<MoveNextAction>(
                            actionParametersOf(
                                ActionParameters.Key<Int>("Index") to index
                            )))
                }
            }
        }
    }

    @Composable
    private fun PokeDex() {
        val context = LocalContext.current
        val index = (state as? WidgetState.Success)?.id ?: 0
        val ready = currentState(ReadyKey) ?: true

        val image = (Coil.imageLoader(context).executeBlocking(
            ImageRequest.Builder(context).data(getImageUrl(index)).build()
        ).drawable as? BitmapDrawable)?.bitmap

//        modifier = GlanceModifier.background(Color.Black).padding(80.dp).background(Color.White)

        Box(modifier = GlanceModifier.fillMaxSize().background(Color.Black).padding(6.dp)
            .cornerRadius(40.dp)) {
            Box(modifier = GlanceModifier.fillMaxSize().background(Color.White).padding(6.dp)
                .cornerRadius(40.dp)) {
                Row(
                    modifier = GlanceModifier.background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ImageButton<MovePrevAction>(R.drawable.ic_left_arrow, index)

                    when (state) {
                        WidgetState.Loading -> {
                            Box(GlanceModifier.defaultWeight()) {
                                Text(text = "Loading . . .")
                            }
                        }
                        is WidgetState.Success -> {
                            image?.let {
                                Row() {
                                    Image(
                                        modifier = GlanceModifier.size(120.dp).padding(4.dp),
                                        provider = ImageProvider(it),
                                        contentDescription = "Pokemon"
                                    )
                                    Column(GlanceModifier.defaultWeight()) {
                                        Text("#03d".format(index))
                                        Text(state.species)
                                        Text("Height")
                                        Text("%.1f m".format(state.height / 10f))
                                        Text("Weight")
                                        Text("%.1f kg".format(state.weight / 10f))
                                    }
                                }
                            }
                        }
                    }

                    ImageButton<MoveNextAction>(R.drawable.ic_right_arrow, index)
                }
            }
        }
    }

    @Composable
    private inline fun <reified T : ActionCallback> ImageButton(
        @DrawableRes imageRes: Int,
        id: Int,
    ) {
        Image(
            modifier = GlanceModifier.padding(4.dp).size(24.dp)
                .clickable(onClick = actionRunCallback<T>(actionParametersOf(
                    ActionParameters.Key<Int>("Index") to id
                ))),
            provider = ImageProvider(imageRes),
            contentDescription = "Pokemon"
        )
    }
}

class MovePrevAction : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        widgetLoader?.prevPokemon(context,
            parameters.get<Int>(ActionParameters.Key<Int>("Index")) ?: 0)
    }
}

class MoveNextAction : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        widgetLoader?.nextPokemon(context,
            parameters.get<Int>(ActionParameters.Key<Int>("Index")) ?: 0)
    }
}


