package agency.five.tmdb

import agency.five.tmdb.navigation.Screens
import agency.five.tmdb.ui.components.MovieCard
import agency.five.tmdb.ui.components.MovieItemViewState
import agency.five.tmdb.ui.theme.Typography
import agency.five.tmdb.ui.viewmodel.FavoritesScreenViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.viewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val favoritesViewModel by viewModel<FavoritesScreenViewModel>()

    val favoritesFlowLifecycleAware =
        remember(favoritesViewModel.getFavoriteMovies(), lifecycleOwner) {
            favoritesViewModel.getFavoriteMovies().flowWithLifecycle(
                lifecycle = lifecycleOwner.lifecycle,
                minActiveState = Lifecycle.State.STARTED
            )
        }

    //val list: List<MovieItemViewState> by favoritesViewModel.getFavoriteMovies().collectAsState(initial = listOf())

    val list: List<MovieItemViewState> by favoritesFlowLifecycleAware.collectAsState(initial = listOf())

    LazyVerticalGrid(
        contentPadding = PaddingValues(horizontal = 18.dp),
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
                    .height(18.dp)
                    .fillMaxSize()
            )
            Spacer(
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxSize()
            )
        }

        for (movie in list) {
            item {

                MovieCard(
                    item = movie,
                    modifier = Modifier.padding(
                        end = dimensionResource(id = R.dimen.movie_list_item_padding),
                        top = dimensionResource(id = R.dimen.micro_spacing),
                        bottom = dimensionResource(id = R.dimen.micro_spacing)
                    ),
                    onMovieItemClick = { navController.navigate(Screens.DetailsScreen.route + "/${movie.id}") },
                    onFavoriteButtonClick = { updatedMovie ->
                        favoritesViewModel.movieFavoriteButtonClick(movie, updatedMovie.favorite)
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun FavoritesScreenPreview() {
    //FavoritesScreen()
}