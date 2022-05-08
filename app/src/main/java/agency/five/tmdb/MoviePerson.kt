package agency.five.tmdb

import agency.five.tmdb.ui.theme.Typography
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class MoviePerson(
    val movie: String,
    val name: String,
    val surname: String,
    val movieFunction: String,
    val role: String = "", // needs to be changed
    val imageResId: Int = -1, // id from drawable folder (R.drawable...) -> needs to be changed
)

@Composable
fun MoviePersonCard(
    person: MoviePerson,
    modifier: Modifier = Modifier,
    onMoviePersonClick: () -> Unit = {}
) {
    Surface(
        elevation = 10.dp,
        color = Color.Transparent
    ) {
        Box(
            modifier = modifier
                .clickable { onMoviePersonClick() }
                .width(dimensionResource(id = R.dimen.movie_person_card_width))
                .height(dimensionResource(id = R.dimen.movie_person_card_height))
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.small_spacing)))
                .background(Color.White)
        ) {
            Column() {
                Image(
                    painter = painterResource(id = person.imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.movie_person_image_height))
                        .width(dimensionResource(id = R.dimen.movie_person_image_width))
                )

                Text(
                    text = person.name + " " + person.surname,
                    style = Typography.subtitle2,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.movie_person_card_name_padding_start),
                        end = dimensionResource(id = R.dimen.movie_person_card_name_padding_end)
                    )
                )

                Text(
                    text = person.role,
                    style = Typography.caption,
                    color = Color(0xFF828282),
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.movie_person_card_name_padding_start)
                        )
                )
            }
        }
    }
}


@Composable
fun MoviePersonList(
    modifier: Modifier = Modifier,
    onMoviePersonClick: (MoviePerson) -> Unit = {},
    persons: List<MoviePerson>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.home_movies_list_content_padding),
            vertical = dimensionResource(id = R.dimen.home_movies_list_content_padding)
        )
    ) {
        items(persons) {
            MoviePersonCard(
                person = it,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.micro_spacing)),
                onMoviePersonClick = { onMoviePersonClick(it) }
            )
        }
    }
}

@Preview
@Composable
fun MoviePersonCardPreview() {

    val p1 = MoviePerson(
        movie = "Iron Man 1",
        name = "Robert",
        surname = "Downey Jr.",
        movieFunction = "Characters",
        role = "Tony Stark/Iron Man",
        imageResId = R.drawable.robert_downey
    )

    MoviePersonCard(person = p1)
}


@Preview
@Composable
fun MoviePersonListPreview() {

    val p1 = MoviePerson(
        movie = "Iron Man 1",
        name = "Robert",
        surname = "Downey Jr.",
        movieFunction = "Characters",
        role = "Tony Stark/Iron Man",
        imageResId = R.drawable.robert_downey
    )

    val p2 = MoviePerson(
        movie = "Iron Man 1",
        name = "Terrence",
        surname = "Howard",
        movieFunction = "Characters",
        role = "James Rhodes",
        imageResId = R.drawable.terrence_howard
    )

    val p3 = MoviePerson(
        movie = "Iron Man 1",
        name = "Jeff",
        surname = "Bridges",
        movieFunction = "Characters",
        role = "Obadiah Stane / Iron Monger",
        imageResId = R.drawable.jeff_bridges
    )

    MoviePersonList(persons = listOf(p1, p2, p3))
}

