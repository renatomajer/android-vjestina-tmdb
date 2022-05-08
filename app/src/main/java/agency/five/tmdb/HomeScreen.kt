package agency.five.tmdb

import agency.five.tmdb.ui.theme.MovieItemViewState
import agency.five.tmdb.ui.theme.Typography
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    map1: Map<String, List<MovieItemViewState>>, // map of movies for the "What's popular" section with keys as tab names
    map2: Map<String, List<MovieItemViewState>>, // map of movies for the "Free to watch" section with keys as tab names
    map3: Map<String, List<MovieItemViewState>>, // map of movies for the "Trending" section with keys as tab names
    navController: NavController
) {

    // list of tabs for the "What's popular" section
    val t1 = listOf(
        stringResource(id = R.string.streaming_tab),
        stringResource(id = R.string.on_tv_tab),
        stringResource(id = R.string.for_rent_tab),
        stringResource(id = R.string.in_theaters_tab)
    )

    // list of tabs for the "Free to watch" section
    val t2 = listOf(
        stringResource(id = R.string.movies_tab),
        stringResource(id = R.string.tv_tab)
    )

    // list of tabs for the "Trending" section
    val t3 = listOf(
        stringResource(id = R.string.today_tab),
        stringResource(id = R.string.this_week_tab)
    )

    LazyColumn(
        modifier = modifier
    ) {

        item {
            SearchBar()
        }

        items(3) { index ->
            if (index == 0) {
                Text(
                    text = stringResource(id = R.string.whats_popular_section),
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.home_screen_content_padding)
                        )
                        .fillMaxWidth(),
                    style = Typography.h6
                )

                TabList(tabData = t1, moviesMap = map1, navController = navController)

            } else if (index == 1) {
                Text(
                    text = stringResource(id = R.string.free_to_watch_section),
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.home_screen_content_padding)
                        )
                        .fillMaxWidth(),
                    style = Typography.h6
                )

                TabList(tabData = t2, moviesMap = map2, navController = navController)

            } else {
                Text(
                    text = stringResource(id = R.string.trending_section),
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.home_screen_content_padding)
                        )
                        .fillMaxWidth(),
                    style = Typography.h6
                )

                TabList(tabData = t3, moviesMap = map3, navController = navController)

            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {

    val m0 = MovieItemViewState(
        id = 0,
        overview = "",
        title = "Iron Man",
        imageResId = R.drawable.iron_man_1
    )

    val m1 = MovieItemViewState(
        id = 1,
        overview = "",
        title = "Gattaca",
        imageResId = R.drawable.gattaca
    )

    val m2 = MovieItemViewState(
        id = 2,
        overview = "",
        title = "Lion King",
        imageResId = R.drawable.lion_king
    )

    val m3 = MovieItemViewState(
        id = 3,
        overview = "",
        title = "Puppy Love",
        imageResId = R.drawable.puppy_love
    )

    val m4 = MovieItemViewState(
        id = 4,
        overview = "",
        title = "Godzila",
        imageResId = R.drawable.godzila
    )

    val m5 = MovieItemViewState(
        id = 5,
        overview = "",
        title = "Jungle Beat",
        imageResId = R.drawable.jungle_beat
    )

    val l1 = listOf(m0, m1, m2, m3)

    val l2 = listOf(m0, m1)

    val l3 = listOf(m4, m5)

    val map1 = mapOf(
        stringResource(id = R.string.streaming_tab) to l1,
        stringResource(id = R.string.on_tv_tab) to l2,
        stringResource(id = R.string.for_rent_tab) to l3,
        stringResource(id = R.string.in_theaters_tab) to l1
    )
    val map2 = mapOf(
        stringResource(id = R.string.movies_tab) to l2,
        stringResource(id = R.string.tv_tab) to l2
    )
    val map3 = mapOf(
        stringResource(id = R.string.today_tab) to l1,
        stringResource(id = R.string.this_week_tab) to l3
    )

    //HomeScreen(map1 = map1, map2 = map2, map3 = map3)
}