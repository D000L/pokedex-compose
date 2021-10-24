package com.doool.pokedex.presentation.ui.main.home

import android.view.KeyEvent
import androidx.activity.compose.BackHandler
import androidx.annotation.ColorRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.doool.pokedex.R
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.presentation.ui.main.GamesDestination
import com.doool.pokedex.presentation.ui.main.ItemDestination
import com.doool.pokedex.presentation.ui.main.LocalNavController
import com.doool.pokedex.presentation.ui.main.LocationDestination
import com.doool.pokedex.presentation.ui.main.common.*
import com.doool.pokedex.presentation.ui.main.move.MoveInfoDestination
import com.doool.pokedex.presentation.ui.main.move.MoveListDestination
import com.doool.pokedex.presentation.ui.main.news.NewsDestination
import com.doool.pokedex.presentation.ui.main.pokemon.detail.PokemonInfoDestination
import com.doool.pokedex.presentation.ui.main.pokemon.list.PokemonListDestination
import com.doool.pokedex.presentation.utils.Process
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen
import com.doool.pokedex.presentation.utils.clipBackground
import com.doool.pokedex.presentation.utils.defaultPlaceholder

enum class Menu(@ColorRes val colorRes: Int, val destination: String) {
  News(R.color.background_psychic, NewsDestination.route),
  Pokemon(R.color.background_flying, PokemonListDestination.route),
  Games(R.color.background_rock, GamesDestination.route),
  Move(R.color.background_grass, MoveListDestination.route),
  Item(R.color.background_poison, ItemDestination.route),
  Berry(R.color.background_normal, LocationDestination.route),
  Location(R.color.background_electric, LocationDestination.route),
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
  onClickMenu: (Menu, String?) -> Unit
) {
  var isSearching by remember { mutableStateOf(false) }
  val animationOffset by animateFloatAsState(targetValue = if (isSearching) 0f else 1f)

  Box(
    Modifier
      .verticalScroll(rememberScrollState())
      .fillMaxWidth()
  ) {
    Text(
      modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 48.dp)
        .alpha(animationOffset),
      text = stringResource(id = R.string.home_title),
      style = MaterialTheme.typography.h1
    )

    MenuScreen(
      Modifier
        .padding(start = 20.dp, end = 20.dp, top = 200.dp)
        .alpha(animationOffset)
    )

    Surface(Modifier.padding(top = 122.dp * animationOffset)) {
      SearchScreen(isSearching, { isSearching = it }, onClickMenu = onClickMenu)
    }
  }

  BackHandler(isSearching) { isSearching = false }
}

@Composable
private fun SearchScreen(
  isExpended: Boolean,
  setExpended: (Boolean) -> Unit,
  viewModel: HomeViewModel = hiltViewModel(),
  onClickMenu: (Menu, String?) -> Unit
) {
  val animate by animateFloatAsState(targetValue = if (isExpended) 0f else 1f)
  val query by viewModel.query.collectAsState()

  Column {
    SearchBox(
      modifier = Modifier
        .padding(horizontal = 20.dp * animate)
        .background(
          color = Color.LightGray.copy(alpha = 0.4f),
          shape = RoundedCornerShape(8.dp * animate)
        )
        .clickable(!isExpended) { setExpended(true) },
      query = query,
      updateQuery = viewModel::search,
      isExpended = isExpended,
      navigateBack = { setExpended(false) }
    )

    if (isExpended && animate == 0f) {
      Space(height = 20.dp)
      SearchResult(
        modifier = Modifier.fillMaxSize(),
        uiState = viewModel.searchResultState.collectAsState(LoadState.Loading).value,
        onClickMore = {
          onClickMenu(it, query)
        })
      Space(height = 20.dp)
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBox(
  modifier: Modifier = Modifier,
  query: String,
  updateQuery: (String) -> Unit,
  isExpended: Boolean = false,
  navigateBack: () -> Unit
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  val focusRequester = remember { FocusRequester() }
  val focusManager = LocalFocusManager.current

  val hideKeyboard by rememberUpdatedState {
    keyboardController?.hide()
    focusManager.clearFocus()
  }

  LaunchedEffect(isExpended) { if (isExpended) focusRequester.requestFocus() }

  BasicTextField(
    enabled = isExpended,
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
    decorationBox = {
      SearchBoxLayout(isExpended, query.isEmpty(), {
        hideKeyboard()
        updateQuery("")
      }, {
        hideKeyboard()
        navigateBack()
      }, it)
    })
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchBoxLayout(
  isExpended: Boolean,
  showHint: Boolean,
  onClickClear: () -> Unit,
  onClickBack: () -> Unit,
  textField: @Composable () -> Unit
) {
  Row(Modifier.padding(horizontal = 24.dp), verticalAlignment = Alignment.CenterVertically) {
    AnimatedVisibility(visible = isExpended) {
      IconButton(modifier = Modifier.size(28.dp), onClick = { onClickBack() }) {
        Icon(
          imageVector = Icons.Default.ArrowBack,
          contentDescription = null
        )
      }
    }

    Box(
      Modifier
        .padding(horizontal = 12.dp)
        .weight(1f)
    ) {
      if (showHint) Text(stringResource(R.string.search_hint))
      textField()
    }

    if (!showHint) {
      IconButton(modifier = Modifier.size(28.dp), onClick = onClickClear) {
        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
      }
    }
  }
}

@Composable
private fun SearchResult(
  modifier: Modifier,
  uiState: LoadState<SearchResult>,
  onClickMore: (Menu) -> Unit
) {
  Column(modifier) {
    uiState.Process(onLoading = {
      SummaryList("Pokemon", (0..6).toList()) { SummaryItemPlaceholder(220.dp) }
      SummaryList("Items", (0..6).toList()) { SummaryItemPlaceholder(92.dp) }
      SummaryList("Moves", (0..6).toList()) { SummaryItemPlaceholder(120.dp) }
    }, onComplete = { result ->
      val navController = LocalNavController.current

      SummaryList("Pokemon", result.pokemon, { onClickMore(Menu.Pokemon) }, {
        PokemonSummary(pokemon = it) {
          navController.navigate(PokemonInfoDestination.getRouteByName(it.name))
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
      .padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = title, style = MaterialTheme.typography.subtitle2, fontWeight = FontWeight.Bold)
    SpaceFill()
    Box(modifier = Modifier.clickable { onClickMore() }) {
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
  content: @Composable (T) -> Unit = {}
) {
  val state = rememberScrollState()

  Column(Modifier.fillMaxWidth()) {
    SummaryTitle(title, onClickMore)
    Space(height = 10.dp)
    Row(
      modifier = Modifier.horizontalScroll(state),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Space(width = 10.dp)
      item.forEach {
        content(it)
      }
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
fun PokemonSummaryPreview() {
  PokemonSummary(PokemonDetail(
    101,
    "electrode",
    14,
    54,
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/101.png",
    listOf(),
    listOf(Info("bug"), Info("fairy")),
    listOf()
  ), {})
}

@Composable
private fun PokemonSummary(pokemon: PokemonDetail, onClick: () -> Unit = {}) {
  Column(
    modifier = Modifier
      .size(220.dp)
      .clipBackground(
        color = colorResource(id = pokemon.getBackgroundColor()),
        shape = RoundedCornerShape(24.dp)
      )
      .clickable {
        onClick()
      }
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
      text = pokemon.name.capitalizeAndRemoveHyphen(),
      style = MaterialTheme.typography.h4,
      color = Color.White
    )
    Space(height = 4.dp)
    Image(
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .size(120.dp),
      painter = rememberImagePainter(pokemon.image),
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
fun ItemSummaryPreview() {
  ItemSummary(Item(
    name = "Ability Urge"
  ), {})
}

@Composable
fun ItemSummary(item: Item, onClick: () -> Unit = {}) {
  Column(
    Modifier
      .size(92.dp)
      .clipBackground(
        color = Color.Gray,
        shape = RoundedCornerShape(16.dp)
      )
      .clickable {
        onClick()
      }
      .padding(12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      modifier = Modifier.size(32.dp),
      painter = rememberImagePainter(item.sprites),
      contentDescription = null
    )
    Box(Modifier.weight(1f, fill = true), contentAlignment = Alignment.Center) {
      Text(
        text = item.name.capitalizeAndRemoveHyphen(),
        style = MaterialTheme.typography.body1,
        color = Color.White,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

@Composable
@Preview
fun PreviewMove() {
  MoveThumbnail(
    PokemonMove(
      name = "Mega Punch",
      id = 1,
      accuracy = 100,
      damageClass = Info(
        name = "physical"
      ),
      flavorTextEntries = listOf("A ramming attack that may cause flinching."),
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
fun MoveThumbnail(move: PokemonMove, onClick: () -> Unit = {}) {
  Column(
    Modifier
      .size(120.dp)
      .clipBackground(
        color = colorResource(id = move.damageClass.name.toMoveCategoryColor().colorRes),
        shape = RoundedCornerShape(20.dp)
      )
      .clickable {
        onClick()
      }
      .padding(10.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Box(Modifier.weight(1f, fill = true), contentAlignment = Alignment.Center) {
      Text(
        text = move.name.capitalizeAndRemoveHyphen(),
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
      Space(width = 4.dp)
      Text(
        text = move.power.toString(),
        style = MaterialTheme.typography.body1,
        color = Color.White
      )
      Space(width = 4.dp)
      Text(
        text = "PP",
        style = MaterialTheme.typography.body2,
        color = Color.White.copy(0.7f)
      )
      Space(width = 4.dp)
      Text(
        text = move.pp.toString(),
        style = MaterialTheme.typography.body1,
        color = Color.White
      )
    }
    Space(height = 4.dp)
    move.type.name.toPokemonType()
      .let { Type(it.typeColorResId, size = 20.dp, fontSize = 13.sp, it.name) }
    Space(height = 4.dp)
  }
}

@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
  val navController = LocalNavController.current

  Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
    MenuItem(Modifier.fillMaxWidth(), navController, Menu.News)

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      MenuItem(Modifier.weight(1f), navController, Menu.Pokemon)
      MenuItem(Modifier.weight(1f), navController, Menu.Move)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      MenuItem(Modifier.weight(1f), navController, Menu.Item)
      MenuItem(Modifier.weight(1f), navController, Menu.Berry)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      MenuItem(Modifier.weight(1f), navController, Menu.Games)
      MenuItem(Modifier.weight(1f), navController, Menu.Location)
    }
  }
}

@Composable
private fun MenuItem(
  modifier: Modifier = Modifier,
  navController: NavController,
  menu: Menu,
) {
  Box(
    modifier = modifier
      .height(92.dp)
      .clipBackground(colorResource(id = menu.colorRes), shape = RoundedCornerShape(8.dp))
      .clickable {
        navController.navigate(menu.destination)
      },
    contentAlignment = Alignment.Center
  ) {
    Pokeball(120.dp, Alignment.CenterEnd, DpOffset(x = 23.dp, y = -25.dp), rotate = 220f)
    Text(text = menu.name, style = MaterialTheme.typography.h4)
  }
}
