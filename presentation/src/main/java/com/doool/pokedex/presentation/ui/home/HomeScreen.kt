package com.doool.pokedex.presentation.ui.home

import androidx.activity.compose.BackHandler
import androidx.annotation.ColorRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.doool.pokedex.R
import com.doool.pokedex.presentation.nav.LocalNavController
import com.doool.pokedex.presentation.ui.main.GamesDestination
import com.doool.pokedex.presentation.ui.main.ItemDestination
import com.doool.pokedex.presentation.ui.main.LocationDestination
import com.doool.pokedex.presentation.ui.move_list.destination.MoveListDestination
import com.doool.pokedex.presentation.ui.news.destination.NewsDestination
import com.doool.pokedex.presentation.ui.pokemon_list.destination.PokemonListDestination
import com.doool.pokedex.presentation.ui.setting.SettingDropDown
import com.doool.pokedex.presentation.ui.widget.PokeBall
import com.doool.pokedex.presentation.ui.widget.SpaceFill
import com.doool.core.utils.clipBackground
import com.doool.core.utils.ifThen

enum class Menu(@ColorRes val colorRes: Int, val destination: String) {
    News(R.color.background_psychic, NewsDestination.route),
    Pokemon(R.color.background_flying, PokemonListDestination.route),
    Move(R.color.background_grass, MoveListDestination.route),
    Item(R.color.background_poison, ItemDestination.route),
    Berry(R.color.background_normal, LocationDestination.route),
    Games(R.color.background_rock, GamesDestination.route),
    Location(R.color.background_electric, LocationDestination.route),
}

@Composable
fun HomeScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onClickMenu: (Menu, String?) -> Unit
) {
    var isSearching by rememberSaveable { mutableStateOf(false) }
    val animationOffset by animateFloatAsState(targetValue = if (isSearching) 0f else 1f)

    val query by viewModel.query.collectAsState()

    Column(
        Modifier
            .fillMaxWidth()
            .ifThen(animationOffset != 0f, Modifier.verticalScroll(rememberScrollState()))
    ) {
        AnimatedVisibility(visible = !isSearching) {
            HomeHeader()
        }

        val searchBoxModifier = Modifier
            .padding(
                top = 40.dp * animationOffset,
                start = 20.dp * animationOffset,
                end = 20.dp * animationOffset
            )
            .background(
                color = Color.LightGray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(8.dp * animationOffset)
            )

        if (!isSearching) {
            Box(
                modifier = searchBoxModifier
                    .height(52.dp)
                    .fillMaxWidth()
                    .clickable(!isSearching) { isSearching = true }
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterStart) {
                Text(stringResource(R.string.search_hint))
            }

            MenuList(
                Modifier
                    .padding(horizontal = 20.dp, vertical = 40.dp)
                    .alpha(animationOffset)
            )
        } else {
            SearchTextField(
                modifier = searchBoxModifier,
                query = query,
                updateQuery = viewModel::search,
                onClickClear = viewModel::clearQuery,
                onClickBack = {
                    viewModel.clearQuery()
                    isSearching = false
                })

            if (animationOffset == 0f) {
                SearchResult {
                    onClickMenu(it, query)
                }
            }
        }
    }

    BackHandler(isSearching) { isSearching = false }
    LaunchedEffect(isSearching) { if (!isSearching) viewModel.clearQuery() }
}

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.home_title),
            style = MaterialTheme.typography.h1
        )

        SpaceFill()

        var showMenu by remember { mutableStateOf(false) }

        IconButton(onClick = { showMenu = true }) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)

            SettingDropDown(expended = showMenu) {
                showMenu = false
            }
        }
    }
}

@Composable
private fun MenuList(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        MenuItem(Modifier.fillMaxWidth(), navController, Menu.values().first())

        Menu.values().drop(1).chunked(2).forEach { (left, right) ->
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                MenuItem(Modifier.weight(1f), navController, left)
                MenuItem(Modifier.weight(1f), navController, right)
            }
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
        PokeBall(
            modifier = Modifier.align(Alignment.CenterEnd),
            size = 120.dp,
            translateOffset = DpOffset(x = 23.dp, y = -25.dp),
            rotate = 220f,
        )
        Text(text = menu.name, style = MaterialTheme.typography.h4)
    }
}
