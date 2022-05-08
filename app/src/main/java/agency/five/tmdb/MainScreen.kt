package agency.five.tmdb

import agency.five.tmdb.ui.theme.MovieItemViewState
import agency.five.tmdb.ui.theme.TmdbTheme
import agency.five.tmdb.ui.theme.Typography
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navController: NavController
) {

    // movies
    val m0 = MovieItemViewState(
        id = 0,
        overview = "Nothing to show.",
        title = "Iron Man",
        imageResId = R.drawable.iron_man_1
    )

    val m1 = MovieItemViewState(
        id = 1,
        overview = "Nothing to show.",
        title = "Gattaca",
        imageResId = R.drawable.gattaca
    )

    val m2 = MovieItemViewState(
        id = 2,
        overview = "Nothing to show.",
        title = "Lion King",
        imageResId = R.drawable.lion_king
    )

    val m3 = MovieItemViewState(
        id = 3,
        overview = "Nothing to show.",
        title = "Puppy Love",
        imageResId = R.drawable.puppy_love
    )

    val m4 = MovieItemViewState(
        id = 4,
        overview = "Nothing to show.",
        title = "Godzila",
        imageResId = R.drawable.godzila
    )

    val m5 = MovieItemViewState(
        id = 5,
        overview = "Nothing to show.",
        title = "Jungle Beat",
        imageResId = R.drawable.jungle_beat
    )

    //list of movies to be shown on different sections tabs
    val l1 = listOf(m0, m1, m2, m3)
    val l2 = listOf(m0, m1)
    val l3 = listOf(m4, m5)

    // map of movies for the "What's popular" section with keys as tab names
    val map1 = mapOf(
        stringResource(id = R.string.streaming_tab) to l1,
        stringResource(id = R.string.on_tv_tab) to l2,
        stringResource(id = R.string.for_rent_tab) to l3,
        stringResource(id = R.string.in_theaters_tab) to l1
    )

    // map of movies for the "Free to watch" section with keys as tab names
    val map2 = mapOf(
        stringResource(id = R.string.movies_tab) to l2,
        stringResource(id = R.string.tv_tab) to l2
    )

    // map of movies for the "Trending" section with keys as tab names
    val map3 = mapOf(
        stringResource(id = R.string.today_tab) to l1,
        stringResource(id = R.string.this_week_tab) to l3
    )


    var home by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { MainScreenTopBar() },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                BottomNavigationItem(   // home icon
                    selected = true,
                    onClick = {
                        if (!home) {
                            home = home.not()
                        }
                    },
                    icon = {

                        if (home) {
                            Icon(
                                Icons.Filled.Home,
                                contentDescription = null,
                                tint = Color(0xFF0B253F)
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Home,
                                contentDescription = null,
                                tint = Color(0xFFBDBDBD)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.home),
                            style = Typography.overline,
                            color = if (home) Color(0xFF0B253F) else Color(0xFFBDBDBD)
                        )
                    }
                )

                BottomNavigationItem(   // favorites icon
                    selected = false,
                    onClick = {
                        if (home) {
                            home = home.not()
                        }
                    },
                    icon = {

                        if (!home) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = null,
                                tint = Color(0xFF0B253F)
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Favorite,
                                contentDescription = null,
                                tint = Color(0xFFBDBDBD)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.favorites),
                            style = Typography.overline,
                            color = if (!home) Color(0xFF0B253F) else Color(0xFFBDBDBD)
                        )
                    }
                )
            }
        }
    ) {
        if (home) {
            HomeScreen(
                modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                map1 = map1,
                map2 = map2,
                map3 = map3,
                navController = navController
            )
        } else {
            FavoritesScreen(
                modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                navController = navController
            ) // movie list is set by default
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TmdbTheme {
        //MainScreen()
    }
}


@Composable
fun MainScreenTopBar() {
    TopAppBar(
        backgroundColor = Color(0xFF0B253F)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier.size(
                    width = dimensionResource(id = R.dimen.top_bar_logo_width),
                    height = dimensionResource(
                        id = R.dimen.top_bar_logo_height
                    )
                )
            )
        }
    }
}