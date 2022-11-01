package com.doool.pokedex.presentation.ui.home

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.doool.pokedex.core.Process
import com.doool.pokedex.core.common.MoveCategoryColor
import com.doool.pokedex.core.common.PokemonType
import com.doool.pokedex.core.extensions.getBackgroundColor
import com.doool.pokedex.core.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.core.utils.clipBackground
import com.doool.pokedex.core.utils.defaultPlaceholder
import com.doool.pokedex.core.utils.localized
import com.doool.pokedex.core.widget.Space
import com.doool.pokedex.core.widget.SpaceFill
import com.doool.pokedex.core.widget.TypeList
import com.doool.pokedex.core.widget.TypeText
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.Effect
import com.doool.pokedex.domain.model.Info
import com.doool.pokedex.domain.model.Item
import com.doool.pokedex.domain.model.LocalizedString
import com.doool.pokedex.domain.model.PokemonDetail
import com.doool.pokedex.domain.model.PokemonMove
import com.doool.pokedex.domain.model.PokemonSpecies
import com.doool.pokedex.move.destination.MoveInfoDestination
import com.doool.pokedex.pokemon.destination.PokemonInfoDestination
import com.doool.pokedex.presentation.ui.main.ItemDestination

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    query: String,
    updateQuery: (String) -> Unit,
    onClickClear: () -> Unit,
    onClickBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    val hideKeyboard by rememberUpdatedState {
        keyboardController?.hide()
    }

    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .height(52.dp)
            .onPreviewKeyEvent {
                if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                    hideKeyboard()
                    true
                } else false
            },
        value = query,
        onValueChange = updateQuery,
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions { hideKeyboard() },
        decorationBox = { textField ->
            SearchFieldLayout(
                showClearButton = query.isEmpty(),
                onClickClear = onClickClear,
                onClickBack = {
                    hideKeyboard()
                    onClickBack()
                },
                textField = textField
            )
        })

    LaunchedEffect(Unit) { focusRequester.requestFocus() }
}

@Composable
private fun SearchFieldLayout(
    showClearButton: Boolean,
    onClickClear: () -> Unit,
    onClickBack: () -> Unit,
    textField: @Composable () -> Unit
) {
    Row(Modifier.padding(horizontal = 24.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(modifier = Modifier.size(28.dp), onClick = onClickBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }

        Space(12.dp)
        textField()
        SpaceFill()

        if (showClearButton) {
            IconButton(modifier = Modifier.size(28.dp), onClick = onClickClear) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }
    }
}

@Composable
fun SearchResult(
    viewModel: SearchViewModel = hiltViewModel(),
    onClickMore: (Menu) -> Unit
) {
    val uiState by viewModel.searchResultState.collectAsState(LoadState.Loading())

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Space(height = 20.dp)
        SearchResult(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            onClickMore = onClickMore
        )
        Space(height = 20.dp)
    }
}

@Composable
private fun SearchResult(
    modifier: Modifier,
    uiState: LoadState<SearchUIModel>,
    onClickMore: (Menu) -> Unit
) {
    Column(modifier) {
        uiState.Process(onLoading = {
            SummaryList("Pokemon", (0..6).toList()) { SummaryItemPlaceholder(220.dp) }
            SummaryList("Items", (0..6).toList()) { SummaryItemPlaceholder(92.dp) }
            SummaryList("Moves", (0..6).toList()) { SummaryItemPlaceholder(120.dp) }
        }, onComplete = { result ->
            val navController = com.doool.pokedex.navigation.LocalNavController.current

            SummaryList("Pokemon", result.pokemon, { onClickMore(Menu.Pokemon) }, {
                PokemonSummary(pokemon = it.first, it.second) {
                    navController.navigate(PokemonInfoDestination.getRouteByName(it.first.name))
                }
            })

            SummaryList("Items", result.items, { onClickMore(Menu.Item) }, {
                ItemSummary(item = it) {
                    navController.navigate(ItemDestination.route)
                }
            })

            SummaryList("Moves", result.moves, { onClickMore(Menu.Move) }, {
                MoveThumbnail(move = it) {
                    navController.navigate(MoveInfoDestination.getRouteByName(it.name))
                }
            })
        })
    }
}

@Composable
private fun SummaryTitle(title: String, onClickMore: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.subtitle2, fontWeight = FontWeight.Bold)
        SpaceFill()
        Box(modifier = Modifier.clickable(onClick = onClickMore)) {
            Text(
                text = "See More",
                style = MaterialTheme.typography.body1,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun <T> SummaryList(
    title: String,
    item: List<T>,
    onClickMore: () -> Unit = {},
    itemContent: @Composable (T) -> Unit = {}
) {
    Column(Modifier.fillMaxWidth()) {
        SummaryTitle(title, onClickMore)
        Space(height = 10.dp)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Space(width = 10.dp)
            item.forEach { itemContent(it) }
            Space(width = 10.dp)
        }
        Space(height = 16.dp)
    }
}

@Composable
private fun SummaryItemPlaceholder(size: Dp, cornerRadius: Dp = 16.dp) {
    Box(
        Modifier
            .size(size)
            .defaultPlaceholder(shape = RoundedCornerShape(cornerRadius))
    )
}

@Composable
@Preview
private fun PokemonSummaryPreview() {
    PokemonSummary(
        PokemonDetail(
            101,
            "electrode",
            14,
            54,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/101.png",
            Info("nidoran-f", 29),
            listOf(),
            listOf(Info("bug"), Info("fairy")),
            listOf()
        ), PokemonSpecies(names = listOf(LocalizedString("electrode")))
    ) {}
}

@Composable
private fun PokemonSummary(
    pokemon: PokemonDetail,
    species: PokemonSpecies,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .size(220.dp)
            .clipBackground(
                color = colorResource(id = pokemon.types.getBackgroundColor()),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "#%03d".format(pokemon.id),
            style = MaterialTheme.typography.body1,
            color = Color.White.copy(0.6f),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = species.names.localized.capitalizeAndRemoveHyphen(),
            style = MaterialTheme.typography.h4,
            color = Color.White
        )
        Space(height = 4.dp)
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(120.dp),
            model = pokemon.image,
            contentDescription = null
        )
        Space(height = 4.dp)
        TypeList(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            types = pokemon.types
        )
    }
}

@Composable
@Preview
private fun ItemSummaryPreview() {
    ItemSummary(
        Item(
            name = "Ability Urge"
        )
    ) {}
}

@Composable
private fun ItemSummary(item: Item, onClick: () -> Unit = {}) {
    val result by generateBitmapPalette(item.sprites)
    val (bitmap, bgColor, textColor) = result

    Column(
        Modifier
            .size(92.dp)
            .clipBackground(
                color = bgColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        bitmap?.let {
            Image(
                modifier = Modifier.size(32.dp),
                bitmap = bitmap,
                contentDescription = null
            )
        }

        Box(Modifier.weight(weight = 1f, fill = true), contentAlignment = Alignment.Center) {
            Text(
                text = item.names.localized.capitalizeAndRemoveHyphen(),
                style = MaterialTheme.typography.body1,
                color = textColor,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun generateBitmapPalette(imageUrl: String): State<Triple<ImageBitmap?, Color, Color>> {
    val context = LocalContext.current

    return produceState(
        initialValue = Triple<ImageBitmap?, Color, Color>(
            null,
            Color.Gray,
            Color.White
        )
    ) {
        val image = ImageRequest.Builder(context)
            .data(imageUrl)
            .apply {
                allowHardware(false)
            }
            .build()

        val bitmap =
            Coil.imageLoader(context).execute(image).drawable?.toBitmap() ?: return@produceState

        Palette.Builder(bitmap).generate {
            val color = it?.lightVibrantSwatch?.rgb?.let { Color(it) } ?: Color.Gray
            val textColor = it?.lightVibrantSwatch?.titleTextColor?.let { Color(it) } ?: Color.White
            value = Triple(bitmap.asImageBitmap(), color, textColor)
        }
    }
}

@Composable
@Preview
private fun PreviewMove() {
    MoveThumbnail(
        PokemonMove(
            name = "Mega Punch",
            id = 1,
            accuracy = 100,
            damageClass = Info(
                name = "physical"
            ),
            flavorTextEntries = listOf(LocalizedString("A ramming attack that may cause flinching.")),
            effectEntries = Effect(
                effect = "Inflicts regular damage.  Has a 30% chance to make the target flinch.",
                shortEffect = "Has a 30% chance to make the target flinch.",
                effectChance = 30
            ),
            power = 70, pp = 15, type = Info(name = "normal")
        )
    )
}

@Composable
private fun MoveThumbnail(move: PokemonMove, onClick: () -> Unit = {}) {
    Column(
        Modifier
            .size(120.dp)
            .clipBackground(
                color = colorResource(id = MoveCategoryColor.from(move.damageClass.name).colorRes),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.weight(weight = 1f, fill = true), contentAlignment = Alignment.Center) {
            Text(
                text = move.names.localized.capitalizeAndRemoveHyphen(),
                style = MaterialTheme.typography.body1,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        Space(height = 8.dp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Power",
                style = MaterialTheme.typography.body2,
                color = Color.White.copy(0.7f)
            )
            Space(width = 2.dp)
            Text(
                text = move.power.toString(),
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            Space(width = 3.dp)
            Text(
                text = "PP",
                style = MaterialTheme.typography.body2,
                color = Color.White.copy(0.7f)
            )
            Space(width = 2.dp)
            Text(
                text = move.pp.toString(),
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
        Space(height = 4.dp)

        val type = PokemonType.from(move.type.name)
        TypeText(
            color = type.typeColorResId,
            size = 20.dp,
            fontSize = 13.sp,
            text = type.name
        )

        Space(height = 4.dp)
    }
}
