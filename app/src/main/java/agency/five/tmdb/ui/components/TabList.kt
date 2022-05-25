package agency.five.tmdb.ui.components


import agency.five.tmdb.R
import agency.five.tmdb.navigation.Screens
import agency.five.tmdb.ui.theme.Typography
import agency.five.tmdb.ui.viewmodel.HomeScreenViewModel
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun TabList(
    tabData: List<String>,
    moviesLists: List<List<MovieItemViewState>>,
    navController: NavController,
    model: HomeScreenViewModel
) {
    var tabIndex by remember { mutableStateOf(0) }

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            backgroundColor = Color.Transparent,
            divider = { TabRowDefaults.Divider(thickness = 5.dp, color = Color.Transparent) },
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.customTabIndicatorOffset(tabPositions[tabIndex]),
                    height = 3.dp,
                    color = Color(0xFF0B253F)
                )
            }
        ) {
            tabData.forEachIndexed { index, name ->
                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index
                    },
                    unselectedContentColor = Color(0xFF828282),
                    selectedContentColor = Color.Black,

                    ) {
                    Text(
                        text = name,
                        style = Typography.subtitle1,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
        val moviesData = moviesLists[tabIndex]
        Log.d("debug_log", "${tabData.first()}: $moviesData")

        if (moviesData.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_connection),
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.no_connection_text_top_padding),
                    bottom = dimensionResource(id = R.dimen.no_connection_text_bottom_padding)
                )
            )
        } else {
            MovieList(
                movieItems = moviesData,
                onMovieItemClick = { navController.navigate(Screens.DetailsScreen.route + "/${it.id}") },
                onFavoriteButtonClick = { updatedMovie ->
                    model.movieFavoriteButtonClick(
                        moviesData.first { updatedMovie.id == it.id },
                        updatedMovie.favorite
                    )
                }
            )
        }
    }
}


fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val indicatorWidth = 75.dp
    val currentTabWidth = currentTabPosition.width
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left + currentTabWidth / 2 - indicatorWidth / 2,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(indicatorWidth)
}


