package agency.five.tmdb


import agency.five.tmdb.navigation.Screens
import agency.five.tmdb.ui.theme.MovieCard
import agency.five.tmdb.ui.theme.MovieItemViewState
import agency.five.tmdb.ui.theme.MovieList
import agency.five.tmdb.ui.theme.Typography
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TabList(
    tabData: List<String>,
    moviesMap: Map<String, List<MovieItemViewState>>,
    navController: NavController
) {
    var tabIndex by remember { mutableStateOf(0) }

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        TabRow(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.home_screen_content_padding)
            ),
            selectedTabIndex = tabIndex,
            backgroundColor = Color.Transparent,
            divider = {
                TabRowDefaults.Divider(
                    thickness = dimensionResource(id = R.dimen.tab_row_divider_thickness),
                    color = Color.Transparent
                )
            },
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .customTabIndicatorOffset(tabPositions[tabIndex]),
                    height = dimensionResource(id = R.dimen.tab_row_indicator_height),
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
                    selectedContentColor = Color.Black
                ) {
                    Text(
                        text = name,
                        style = Typography.subtitle1,
                        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.tab_text_vertical_padding))
                    )
                }
            }
        }

        MovieList(
            movieItems = moviesMap.getValue(key = tabData[tabIndex]),
            onMovieItemClick = { navController.navigate(Screens.DetailsScreen.route + "/${it.id}") }
        )
    }
}


@Preview
@Composable
fun TabListPreview() {

    val l1 = listOf(
        MovieItemViewState(
            id = 0,
            overview = "",
            title = "Iron Man",
            imageResId = R.drawable.iron_man_1
        ),

        MovieItemViewState(
            id = 1,
            overview = "",
            title = "Gattaca",
            imageResId = R.drawable.gattaca
        ),

        MovieItemViewState(
            id = 2,
            overview = "",
            title = "Lion King",
            imageResId = R.drawable.lion_king
        ),

        MovieItemViewState(
            id = 3,
            overview = "",
            title = "Puppy Love",
            imageResId = R.drawable.puppy_love
        )
    )

    val l2 = listOf(
        MovieItemViewState(
            id = 0,
            overview = "",
            title = "Iron Man",
            imageResId = R.drawable.iron_man_1
        ),

        MovieItemViewState(
            id = 1,
            overview = "",
            title = "Gattaca",
            imageResId = R.drawable.gattaca
        )
    )

    /*TabList(tabData = listOf(
        stringResource(id = R.string.streaming_tab),
        stringResource(id = R.string.on_tv_tab),
        stringResource(id = R.string.for_rent_tab),
        stringResource(id = R.string.in_theaters_tab)
    ),
        moviesMap = mapOf(
            stringResource(id = R.string.streaming_tab) to l1,
            stringResource(id = R.string.on_tv_tab) to l2,
            stringResource(id = R.string.for_rent_tab) to l1,
            stringResource(id = R.string.in_theaters_tab) to l2
        )
    ) */
}


fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val indicatorWidth = dimensionResource(id = R.dimen.tab_indicator_width)
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


