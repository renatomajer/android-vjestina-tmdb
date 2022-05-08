package agency.five.tmdb

import agency.five.tmdb.navigation.Screens
import agency.five.tmdb.ui.theme.MovieCard
import agency.five.tmdb.ui.theme.MovieItemViewState
import agency.five.tmdb.ui.theme.Typography
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

val m0 = MovieItemViewState(
    id = 0,
    overview = "Nothing to show.",
    title = "Iron Man",
    imageResId = R.drawable.iron_man_1,
    favorite = true
)

val m1 = MovieItemViewState(
    id = 1,
    overview = "Nothing to show.",
    title = "Gattaca",
    imageResId = R.drawable.gattaca,
    favorite = true
)

val m2 = MovieItemViewState(
    id = 2,
    overview = "Nothing to show.",
    title = "Lion King",
    imageResId = R.drawable.lion_king,
    favorite = true
)

val m3 = MovieItemViewState(
    id = 3,
    overview = "Nothing to show.",
    title = "Puppy Love",
    imageResId = R.drawable.puppy_love,
    favorite = true
)

val m4 = MovieItemViewState(
    id = 4,
    overview = "Nothing to show.",
    title = "Godzila",
    imageResId = R.drawable.godzila,
    favorite = true
)

val m5 = MovieItemViewState(
    id = 5,
    overview = "Nothing to show.",
    title = "Jungle Beat",
    imageResId = R.drawable.jungle_beat,
    favorite = true
)

val moviesList = listOf(m0, m1, m2, m3, m4, m5)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    moviesList: List<MovieItemViewState> = listOf(
        m0,
        m1,
        m2,
        m3,
        m4,
        m5
    ),   // movies list is set by default
    navController: NavController
) {

    LazyVerticalGrid(
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.favorites_screen_content_horizontal_padding)),
        cells = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.large_spacing))
    ) {

        item {
            Text(
                text = stringResource(id = R.string.favorites_section),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.medium_spacing)),
                style = Typography.h6
            )
        }

        items(2) {
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.favorites_screen_spacer_height))
                    .fillMaxSize()
            )
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.favorites_screen_spacer_height))
                    .fillMaxSize()
            )
        }

        for (movie in moviesList) {
            item {

                MovieCard(
                    item = movie,
                    modifier = Modifier.padding(
                        end = dimensionResource(id = R.dimen.movie_list_item_padding),
                        top = dimensionResource(id = R.dimen.micro_spacing),
                        bottom = dimensionResource(id = R.dimen.micro_spacing)
                    ),
                    onMovieItemClick = { navController.navigate(Screens.DetailsScreen.route + "/${movie.id}") }
                )
            }
        }
    }
}


@Preview
@Composable
fun FavoritesScreenPreview() {

    //FavoritesScreen(moviesList = moviesList)
}