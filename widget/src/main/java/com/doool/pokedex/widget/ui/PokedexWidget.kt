package com.doool.pokedex.widget.ui

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import coil.Coil
import coil.executeBlocking
import coil.request.ImageRequest
import com.doool.pokedex.widget.R

fun getImageUrl(id: Int) =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

const val IndexKey = "Index"
val ReadyKey = booleanPreferencesKey("ready")

sealed class WidgetState {
    object Loading : WidgetState()
    data class Success(
        val id: Int = 1,
        val name: String = "",
        val height: Int = 0,
        val weight: Int = 0,
        val species: String = "",
        val description: String = "",
    ) : WidgetState()
}

class PokedexWidget(val state: WidgetState) : GlanceAppWidget() {
    override val sizeMode = SizeMode.Exact

    @Composable
    override fun Content() {
        PokeDexBig(state)
    }
}


@Composable
private fun PokeDexBig(state: WidgetState) {
    Column(GlanceModifier.fillMaxSize().background(ColorProvider(R.color.red)).padding(20.dp)) {

        DexInfo(GlanceModifier.defaultWeight().fillMaxWidth(), state)

        Spacer(GlanceModifier.width(20.dp))

        Row(GlanceModifier.height(90.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {

            PokemonDescription(GlanceModifier.defaultWeight(),state)
            Spacer(GlanceModifier.width(20.dp))
            CrossController(state)
        }
    }
}

@Composable
private fun DexInfo(modifier: GlanceModifier = GlanceModifier, state: WidgetState) {
    Row(modifier.background(ImageProvider(R.drawable.background)).padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically) {

        PokemonImage(GlanceModifier.defaultWeight().size(120.dp).padding(4.dp), state)
        PokemonStatus(GlanceModifier.defaultWeight(), state)
    }
}

@Composable
private fun PokemonDescription(modifier: GlanceModifier = GlanceModifier, state: WidgetState) {
    Box(modifier.height(90.dp).background(Color.Black)) {
        when(state){
            WidgetState.Loading -> {}
            is WidgetState.Success -> {
                Text(state.description, style = TextStyle(color = ColorProvider(R.color.white)))
            }
        }
    }
}

@Composable
private fun PokemonImage(modifier: GlanceModifier = GlanceModifier, state: WidgetState) {
    when (state) {
        WidgetState.Loading -> {
            Box(modifier) {}
        }
        is WidgetState.Success -> {
            val context = LocalContext.current

            val image = (Coil.imageLoader(context).executeBlocking(
                ImageRequest.Builder(context).data(getImageUrl(state.id)).build()
            ).drawable as? BitmapDrawable)?.bitmap

            image?.let {
                Image(
                    modifier = modifier,
                    provider = ImageProvider(it),
                    contentDescription = "Pokemon"
                )
            } ?: run {
                Box(modifier) {}
            }
        }
    }
}

@Composable
private fun PokemonStatus(modifier: GlanceModifier = GlanceModifier, state: WidgetState) {
    Column(modifier) {
        when (state) {
            WidgetState.Loading -> {

            }
            is WidgetState.Success -> {
                Text("#%03d".format(state.id))
                Text(state.species)
                Text("Height")
                Text("%.1f m".format(state.height / 10f))
                Text("Weight")
                Text("%.1f kg".format(state.weight / 10f))
            }
        }
    }
}

@Composable
private fun CrossController(state: WidgetState) {
    Row(GlanceModifier.size(90.dp).background(ImageProvider(R.drawable.keypad)),
        verticalAlignment = Alignment.CenterVertically) {

        when (state) {
            WidgetState.Loading -> {
                Box(GlanceModifier.size(30.dp)) {}
                Box(GlanceModifier.size(30.dp, 90.dp)) {}
                Box(GlanceModifier.size(30.dp)) {}
            }
            is WidgetState.Success -> {
                ControllerButton<MovePrevAction>(state.id)
                Box(GlanceModifier.size(30.dp, 90.dp)) {}
                ControllerButton<MoveNextAction>(state.id)
            }
        }
    }
}

@Composable
private inline fun <reified T : ActionCallback> ControllerButton(index: Int) {
    Button(text = "",
        modifier = GlanceModifier.size(30.dp).background(Color.Transparent),
        onClick = actionRunCallback<T>(
            actionParametersOf(
                ActionParameters.Key<Int>("Index") to index
            )))
}

class MovePrevAction : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        widgetLoader?.prevPokemon(context,
            parameters.get<Int>(ActionParameters.Key<Int>(IndexKey)) ?: 0)
    }
}

class MoveNextAction : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        widgetLoader?.nextPokemon(context,
            parameters.get<Int>(ActionParameters.Key<Int>(IndexKey)) ?: 0)
    }
}


