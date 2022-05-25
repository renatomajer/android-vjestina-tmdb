package agency.five.tmdb

import agency.five.tmdb.ui.components.ActorCardsList
import agency.five.tmdb.ui.components.MovieDetails
import agency.five.tmdb.ui.components.MovieItemViewState
import agency.five.tmdb.ui.theme.Typography
import agency.five.tmdb.ui.viewmodel.DetailsScreenViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    id: Int    // movie id
) {
    val movieId = id //?: 0

    val movieDetailsViewModel by viewModel<DetailsScreenViewModel> { parametersOf(id) }

    val lifecycleOwner = LocalLifecycleOwner.current

    val itemFlowLifecycleAware = remember(movieDetailsViewModel.getMovie(movieId), lifecycleOwner) {
        movieDetailsViewModel.getMovie(movieId).flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    val personFunctionsFlowLifecycleAware =
        remember(movieDetailsViewModel.getPersonFunctions(movieId), lifecycleOwner) {
            movieDetailsViewModel.getPersonFunctions(movieId).flowWithLifecycle(
                lifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            )
        }

    val actorsFlowLifecycleAware =
        remember(movieDetailsViewModel.getActors(movieId), lifecycleOwner) {
            movieDetailsViewModel.getActors(movieId).flowWithLifecycle(
                lifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            )
        }

    val item: MovieItemViewState by itemFlowLifecycleAware
        .collectAsState(initial = MovieItemViewState())

    val personFunctions: List<PersonFunction> by personFunctionsFlowLifecycleAware
        .collectAsState(initial = emptyList())

    val actors: List<Actor> by actorsFlowLifecycleAware
        .collectAsState(initial = emptyList()) //list of "Top Billed Cast"

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF0B253F)
            ) {

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Outlined.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(end = 48.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = null,
                        modifier = Modifier.size(width = 134.dp, height = 35.dp)
                    )

                }
            }
        }
    ) {
        LazyColumn() {

            item {
                MovieDetails(item = item, modifier = Modifier.fillMaxWidth())
            }

            items(count = 1) {

                Column(
                    modifier = Modifier.padding(
                        start = 18.dp,
                        end = 30.dp,
                        top = 20.dp,
                        bottom = 22.dp
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.overview),
                        style = Typography.h6,
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.micro_spacing))
                    )

                    Text(
                        text = item.overview,
                        style = Typography.body2,
                        color = Color.Black
                    )
                }
            }

            gridItems(
                data = personFunctions,
                columnCount = 3,
                modifier = Modifier.padding(start = 18.dp, end = 13.dp, bottom = 25.dp),
                horizontalArrangement = Arrangement.Start
            ) { itemData ->
                PersonFunction(person = itemData)
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 13.dp, bottom = 8.dp, top = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(id = R.string.top_billed_cast),
                        style = Typography.h6
                    )

                    Text(
                        text = stringResource(id = R.string.full_cast_crew),
                        style = Typography.subtitle2,
                        color = Color(0xFF0B253F)
                    )
                }
            }

            item {

                if (actors.isEmpty()) {
                    Text(
                        text = "Nothing to show.",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                } else {
                    ActorCardsList(actors = actors)
                }
            }
        }
    }
}


@Composable
fun PersonFunction(
    person: PersonFunction
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = person.name,
            style = Typography.subtitle2,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )

        Text(
            text = person.department,
            style = Typography.body2,
            color = Color.Black
        )
    }
}


@Preview
@Composable
fun PersonRolePreview() {
/*
    val p1 = PersonFunction(
        name = "Don",
        surname = "Heck",
        movieFunction = "Characters"
    )
    PersonFunction(person = p1)

 */
}


// display grid in lazy column
fun <T> LazyListScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}