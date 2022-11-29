package com.doool.pokedex.widget.ui

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import coil.Coil
import coil.executeBlocking
import coil.request.ImageRequest
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.Urls
import com.doool.pokedex.widget.R

private val PokemonIdParamKey = ActionParameters.Key<Int>("PokemonId")
val ReadyPrefKey = booleanPreferencesKey("ready")

class PokedexWidget(private val state: LoadState<WidgetUIModel>) : GlanceAppWidget() {
    override val sizeMode = SizeMode.Exact

    @Composable
    override fun Content() {
        val isReady = currentState(key = ReadyPrefKey) ?: false

        if (!isReady) {
            Warning(message = "You need launch Pokedex at least once")
        } else {
            when (state) {
                is LoadState.Error -> Warning(message = "Error")
                is LoadState.Loading -> {
                    val model = state.partialData ?: return Warning(message = "Error")
                    PokeDex(state = model)
                }
                is LoadState.Success -> {
                    PokeDex(state = state.data)
                }
            }
        }
    }
}

@Composable
private fun Warning(message: String) {
    Box(GlanceModifier.fillMaxSize()) {
        Text(text = message)
    }
}

@Composable
private fun PokeDex(state: WidgetUIModel) {
    Column(GlanceModifier.fillMaxSize().background(ColorProvider(R.color.red)).padding(20.dp)) {
        PokemonInfo(GlanceModifier.defaultWeight().fillMaxWidth(), state)

        Spacer(GlanceModifier.width(20.dp))

        Row(
            GlanceModifier.height(90.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokemonDescription(GlanceModifier.defaultWeight(), state.species, state.description)
            Spacer(GlanceModifier.width(20.dp))
            CrossController(state.id)
        }
    }
}

@Composable
private fun PokemonInfo(modifier: GlanceModifier = GlanceModifier, state: WidgetUIModel) {
    Row(
        modifier.background(ImageProvider(R.drawable.background)).padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        with(state) {
            PokemonImage(
                GlanceModifier.defaultWeight().size(120.dp).padding(4.dp),
                Urls.getImageUrl(id)
            )
            Spacer(GlanceModifier.width(4.dp).background(Color.White))
            PokemonStatus(GlanceModifier.defaultWeight(), id, name, height, weight)
        }
    }
}

@Composable
private fun PokemonImage(modifier: GlanceModifier = GlanceModifier, url: String) {
    val context = LocalContext.current

    val image = (Coil.imageLoader(context).executeBlocking(
        ImageRequest.Builder(context).data(url).build()
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

@Composable
private fun PokemonStatus(
    modifier: GlanceModifier = GlanceModifier,
    index: Int,
    name: String,
    height: Int,
    weight: Int,
) {
    Column(modifier) {
        Text("#%03d".format(index), style = TextStyle(fontSize = 11.sp))
        Text(name, style = TextStyle(fontSize = 16.sp), maxLines = 1)

        Spacer(GlanceModifier.height(6.dp))

        Row(GlanceModifier.fillMaxWidth()) {
            Column(GlanceModifier.defaultWeight()) {
                Text("Height", style = TextStyle(fontSize = 14.sp))
                Text(
                    if (height != 0) "%.1f m".format(height / 10f) else "",
                    style = TextStyle(fontSize = 12.sp)
                )
            }
            Column(GlanceModifier.defaultWeight()) {
                Text("Weight", style = TextStyle(fontSize = 14.sp))
                Text(
                    if (height != 0) "%.1f kg".format(weight / 10f) else "",
                    style = TextStyle(fontSize = 12.sp)
                )
            }
        }
    }
}

@Composable
private fun PokemonDescription(
    modifier: GlanceModifier = GlanceModifier,
    species: String,
    description: String
) {
    Column(modifier.height(90.dp).background(Color.Black).padding(8.dp)) {
        Text(species, style = TextStyle(color = ColorProvider(R.color.white), fontSize = 12.sp))
        Spacer(GlanceModifier.height(2.dp))
        Text(description, style = TextStyle(color = ColorProvider(R.color.white), fontSize = 10.sp))
    }
}

@Composable
private fun CrossController(index: Int) {
    Row(
        GlanceModifier.size(90.dp).background(ImageProvider(R.drawable.keypad)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ControllerButton((index - 1).coerceAtLeast(1))
        Box(GlanceModifier.size(30.dp, 90.dp)) {}
        ControllerButton(index + 1)
    }
}

@Composable
private fun ControllerButton(index: Int) {
    val action = actionRunCallback<ChangePokemon>(actionParametersOf(PokemonIdParamKey to index))
    Box(GlanceModifier.size(30.dp).background(Color.Transparent).clickable(action)) {}
}

class ChangePokemon : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val pokemonId = parameters[PokemonIdParamKey] ?: 0
        widgetActions?.loadPokemon(context, pokemonId)
    }
}
