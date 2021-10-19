package com.doool.pokedex.presentation.ui.main.home

import android.view.KeyEvent
import androidx.annotation.ColorRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.doool.pokedex.R
import com.doool.pokedex.domain.model.*
import com.doool.pokedex.presentation.ui.main.common.*
import com.doool.pokedex.presentation.utils.capitalizeAndRemoveHyphen

enum class Menu {
  News, Pokemon, Games, Move, Item, Berry, Location
}

@Composable
fun HomeScreen(
  viewModel: HomeViewModel = hiltViewModel(),
  onClickMenu: (Menu, String?) -> Unit,
  onClickDetail: (Menu, String) -> Unit
) {
  val isSearching by remember { viewModel.isSearching }.collectAsState(initial = false)

  Column(
    Modifier
      .verticalScroll(rememberScrollState())
      .fillMaxWidth()
      .padding(horizontal = 20.dp)
  ) {
    Space(height = 48.dp)
    Text(
      text = stringResource(id = R.string.home_title),
      style = MaterialTheme.typography.h1
    )
    Space(height = 24.dp)

    Search(doSearch = viewModel::search)

    if (isSearching) {
      Space(height = 20.dp)
      SearchScreen(
        uiState = remember { viewModel.searchUIState() }.collectAsState(
          initial = SearchUIState(
            isLoading = true
          )
        ).value, onClickMore = {
          onClickMenu(it, viewModel.query.value)
        }, onClickItem = { menu, item ->
          onClickDetail(menu, item)
        }
      )
      Space(height = 20.dp)
    } else {
      MenuScreen(Modifier.padding(vertical = 44.dp)) {
        onClickMenu(it, null)
      }
    }
  }
}

@Composable
fun Search(modifier: Modifier = Modifier, doSearch: (String) -> Unit) {
  var query by remember { mutableStateOf("") }
  val focus = LocalFocusManager.current

  val searchAndKeyboardHide by rememberUpdatedState {
    doSearch(query)
    focus.clearFocus()
  }

  BasicTextField(
    modifier = modifier
      .height(52.dp)
      .background(color = Color.LightGray.copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
      .onPreviewKeyEvent {
        if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
          searchAndKeyboardHide()
          true
        } else false
      },
    value = query,
    onValueChange = {
      query = it
      doSearch(query)
    },
    textStyle = MaterialTheme.typography.body1,
    singleLine = true,
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions {
      searchAndKeyboardHide()
    },
    decorationBox = {
      SearchLayout(
        showHint = query.isEmpty(),
        onClickSearch = searchAndKeyboardHide,
        onClickClear = {
          query = ""
          searchAndKeyboardHide()
        },
        textField = it
      )
    })
}

@Composable
fun SearchLayout(
  showHint: Boolean,
  onClickSearch: () -> Unit,
  onClickClear: () -> Unit,
  textField: @Composable () -> Unit
) {
  Row(
    Modifier.padding(horizontal = 24.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(modifier = Modifier.size(24.dp), onClick = onClickSearch) {
      Icon(imageVector = Icons.Default.Search, contentDescription = null)
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
fun SearchScreen(
  uiState: SearchUIState,
  onClickMore: (Menu) -> Unit,
  onClickItem: (Menu, String) -> Unit
) {
  Column(Modifier.fillMaxSize()) {
    if (uiState.isLoading) {
      CircularProgressIndicator()
    } else {
      ThumbnailItem("Pokemon", uiState.pokemon, { onClickMore(Menu.Pokemon) }, {
        PokemonThumbnail(pokemon = it) {
          onClickItem(Menu.Pokemon, it.name)
        }
      })

      ThumbnailItem("Items", uiState.items, { onClickMore(Menu.Item) }, {
        ItemThumbnail(item = it) {
          onClickItem(Menu.Item, it.name)
        }
      })

      ThumbnailItem("Moves", uiState.moves, { onClickMore(Menu.Move) }, {
        MoveThumbnail(move = it) {
          onClickItem(Menu.Move, it.name)
        }
      })
    }
  }
}

@Composable
fun Title(title: String, onClickMore: () -> Unit = {}) {
  Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
    Text(text = title,  style = MaterialTheme.typography.subtitle2, fontWeight = FontWeight.Bold)
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
fun <T> ThumbnailItem(
  title: String,
  item: List<T>?,
  onClickMore: () -> Unit = {},
  content: @Composable (T) -> Unit = {}
) {
  val state = rememberScrollState()

  item?.let {
    Column(Modifier.fillMaxWidth()) {
      Title(title, onClickMore)
      Space(height = 10.dp)
      Row(
        modifier = Modifier.horizontalScroll(state),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        it.forEach {
          content(it)
        }
      }
      Space(height = 16.dp)
    }
  }
}

@Composable
@Preview
fun PokemonThumbnauilPreview() {
  PokemonThumbnail(PokemonDetail(
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
fun PokemonThumbnail(pokemon: PokemonDetail, onClick: () -> Unit = {}) {
  if (pokemon.id == -1) {
    Box(
      Modifier
        .size(220.dp)
        .background(color = Color.Black, shape = RoundedCornerShape(16.dp))
    )
  } else {
    Column(
      modifier = Modifier
        .size(220.dp)
        .background(
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
      Text(text = pokemon.name.capitalizeAndRemoveHyphen(),style = MaterialTheme.typography.h4, color = Color.White)
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
}

@Composable
@Preview
fun ItemThumbnailPreview() {
  ItemThumbnail(Item(
    name = "Ability Urge"
  ), {})
}

@Composable
fun ItemThumbnail(item: Item, onClick: () -> Unit = {}) {
  if (item.id == -1) {
    Box(
      Modifier
        .size(92.dp)
        .background(Color.Black, shape = RoundedCornerShape(16.dp))
    )
  } else {
    Column(
      Modifier
        .size(92.dp)
        .background(
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
}

@Composable
@Preview
fun PreviewMove() {
  MoveThumbnail(
    PokemonMove(
      name = "Mega Punch",
      id = 1,
      accuracy = 100,
      damageClass = com.doool.pokedex.domain.model.Info(
        name = "physical"
      ),
      flavorTextEntries = listOf("A ramming attack that may cause flinching."),
      effectEntries = Effect(
        effect = "Inflicts regular damage.  Has a 30% chance to make the target flinch.",
        shortEffect = "Has a 30% chance to make the target flinch.",
        effectChance = 30
      ),
      power = 70, pp = 15, type = com.doool.pokedex.domain.model.Info(name = "normal")
    )
  )
}

@Composable
fun MoveThumbnail(move: PokemonMove, onClick: () -> Unit = {}) {
  if (move.id == -1) {
    Box(
      Modifier
        .size(92.dp)
        .background(Color.Black, shape = RoundedCornerShape(16.dp))
    )
  } else {
    Column(
      Modifier
        .size(120.dp)
        .background(
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
}

@Composable
fun MenuScreen(modifier : Modifier = Modifier,onClickMenu: (Menu) -> Unit) {
  Column(modifier = modifier,verticalArrangement = Arrangement.spacedBy(20.dp)) {
    MenuItem(
      Modifier.fillMaxWidth(), Menu.News, R.color.background_psychic, onClickMenu
    )

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      MenuItem(
        Modifier.weight(1f), Menu.Pokemon, R.color.background_flying, onClickMenu
      )
      MenuItem(
        Modifier.weight(1f), Menu.Move, R.color.background_grass, onClickMenu
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      MenuItem(
        Modifier.weight(1f), Menu.Item, R.color.background_poison, onClickMenu
      )
      MenuItem(
        Modifier.weight(1f), Menu.Berry, R.color.background_normal, onClickMenu
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      MenuItem(
        Modifier.weight(1f), Menu.Games, R.color.background_rock, onClickMenu
      )
      MenuItem(
        Modifier.weight(1f), Menu.Location, R.color.background_electric, onClickMenu
      )
    }
  }
}

@Composable
private fun MenuItem(
  modifier: Modifier = Modifier,
  menu: Menu,
  @ColorRes color: Int,
  onClickMenu: (Menu) -> Unit
) {


  Box(
    modifier = modifier
      .height(92.dp)
      .background(colorResource(id = color), shape = RoundedCornerShape(8.dp))
      .clickable {
        onClickMenu(menu)
      },
    contentAlignment = Alignment.Center
  ) {
    Pokeball(120.dp, Alignment.CenterEnd, DpOffset(x = 23.dp, y = -25.dp), rotate = 220f)
    Text(text = menu.name, style = MaterialTheme.typography.h4)
  }
}
