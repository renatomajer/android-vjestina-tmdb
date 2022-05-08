package agency.five.tmdb

import agency.five.tmdb.ui.theme.MovieItemViewState
import agency.five.tmdb.ui.theme.Typography
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    id: Int?    // movie id
) {

    val item: MovieItemViewState
    val persons: List<MoviePerson>
    val roles: List<MoviePerson>   //list of "Top Billed Cast"

    if (id != null) {
        item = movies[id]
    } else {
        item = movie0
    }

    if (id == 0) {
        persons = listOf(p1, p2, p3, p4, p5, p6)
        roles = listOf(role1, role2, role3)
    } else {
        persons = listOf()
        roles = listOf()
    }

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
                        .padding(end = dimensionResource(id = R.dimen.details_screen_box_end_padding))
                        .fillMaxSize(),
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
    ) {

        LazyColumn() {

            item {
                MovieDetails(item = item, modifier = Modifier.fillMaxWidth())
            }

            items(count = 1) {

                Column(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.details_screen_overview_start_padding),
                        end = dimensionResource(id = R.dimen.details_screen_overview_end_padding),
                        top = dimensionResource(id = R.dimen.details_screen_overview_top_padding),
                        bottom = dimensionResource(id = R.dimen.details_screen_overview_bottom_padding)
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
                data = persons,
                columnCount = 3,
                modifier = Modifier.padding(
                    start = 18.dp,
                    end = 13.dp,
                    bottom = 25.dp
                ),
                horizontalArrangement = Arrangement.Start
            ) { itemData ->
                PersonRole(person = itemData)
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.details_screen_top_billed_cast_title_start_padding),
                            end = dimensionResource(id = R.dimen.details_screen_top_billed_cast_title_end_padding),
                            bottom = dimensionResource(id = R.dimen.details_screen_top_billed_cast_title_bottom_padding),
                            top = dimensionResource(id = R.dimen.details_screen_top_billed_cast_title_top_padding)
                        )
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

                if (roles.isEmpty()) {
                    Text(
                        text = "Nothing to show.",
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.details_screen_no_roles_start_padding))
                    )
                } else {
                    MoviePersonList(persons = roles)
                }
            }
        }
    }
}


/* mocked data */
val movie0 = MovieItemViewState(
    id = 0,
    title = "Iron Man 1",
    overview = "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.",
    imageResId = R.drawable.iron_man_1_medium,
    date = "05/02/2008",
    userScore = 76,
    country = "US",
    duration = "2h 6m",
    genres = mutableListOf("Action", "Science Fiction", "Adventure")
)

val movies = listOf(movie0, m1, m2, m3, m4, m5)

// Iron Man 1 persons involved
val p1 = MoviePerson(
    movie = "Iron Man 1",
    name = "Don",
    surname = "Heck",
    movieFunction = "Characters"
)

val p2 = MoviePerson(
    movie = "Iron Man 1",
    name = "Jack",
    surname = "Kirby",
    movieFunction = "Characters"
)
val p3 = MoviePerson(
    movie = "Iron Man 1",
    name = "Jon",
    surname = "Favreau",
    movieFunction = "Director"
)

val p4 = MoviePerson(
    movie = "Iron Man 1",
    name = "Don",
    surname = "Heck",
    movieFunction = "Screenplay"
)

val p5 = MoviePerson(
    movie = "Iron Man 1",
    name = "Jack",
    surname = "Marcum",
    movieFunction = "Screenplay"
)

val p6 = MoviePerson(
    movie = "Iron Man 1",
    name = "Matt",
    surname = "Holloway",
    movieFunction = "Screenplay"
)

// Iron Man 1 movie roles
val role1 = MoviePerson(
    movie = "Iron Man 1",
    name = "Robert",
    surname = "Downey Jr.",
    movieFunction = "Characters",
    role = "Tony Stark/Iron Man",
    imageResId = R.drawable.robert_downey
)

val role2 = MoviePerson(
    movie = "Iron Man 1",
    name = "Terrence",
    surname = "Howard",
    movieFunction = "Characters",
    role = "James Rhodes",
    imageResId = R.drawable.terrence_howard
)

val role3 = MoviePerson(
    movie = "Iron Man 1",
    name = "Jeff",
    surname = "Bridges",
    movieFunction = "Characters",
    role = "Obadiah Stane / Iron Monger",
    imageResId = R.drawable.jeff_bridges
)


@Preview
@Composable
fun DetailsScreenPreview() {
    //DetailsScreen(item = item, persons = listOf(p1, p2, p3, p4, p5, p6), roles = listOf(role1, role2, role3))
}

@Composable
fun PersonRole(
    person: MoviePerson
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = person.name + " " + person.surname,
            style = Typography.subtitle2,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )

        Text(
            text = person.movieFunction,
            style = Typography.body2,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun PersonRolePreview() {

    val p1 = MoviePerson(
        movie = "Iron Man 1",
        name = "Don",
        surname = "Heck",
        movieFunction = "Characters"
    )

    PersonRole(person = p1)
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