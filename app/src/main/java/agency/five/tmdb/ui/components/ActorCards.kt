package agency.five.tmdb.ui.components

import agency.five.tmdb.Actor
import agency.five.tmdb.R
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun ActorCard(
    actor: Actor,
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
                    painter = rememberImagePainter("https://image.tmdb.org/t/p/w200" + actor.profile_path),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(154.dp)
                        .width(125.dp)
                )

                Text(
                    text = actor.name,
                    style = Typography.subtitle2,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.movie_person_card_name_padding_start),
                        end = dimensionResource(id = R.dimen.movie_person_card_name_padding_end)
                    )
                )

                Text(
                    text = actor.character,
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
fun ActorCardsList(
    modifier: Modifier = Modifier,
    onMoviePersonClick: (Actor) -> Unit = {},
    actors: List<Actor>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.home_movies_list_content_padding),
            vertical = dimensionResource(id = R.dimen.home_movies_list_content_padding)
        )
    ) {
        items(actors) {
            ActorCard(
                actor = it,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.micro_spacing)),
                onMoviePersonClick = { onMoviePersonClick(it) }
            )
        }
    }
}


@Preview
@Composable
fun ActorCardPreview() {
/*
    val a1 = Actor(
        movie = "Iron Man 1",
        name = "Robert",
        surname = "Downey Jr.",
        role = "Tony Stark/Iron Man",
        imageResId = R.drawable.robert_downey
    )

    ActorCard(actor = a1)
 */
}


@Preview
@Composable
fun ActorCardsListPreview() {
/*
    val a1 = Actor(
        movie = "Iron Man 1",
        name = "Robert",
        surname = "Downey Jr.",
        role = "Tony Stark/Iron Man",
        imageResId = R.drawable.robert_downey
    )

    val a2 = Actor(
        movie = "Iron Man 1",
        name = "Terrence",
        surname = "Howard",
        role = "James Rhodes",
        imageResId = R.drawable.terrence_howard
    )

    val a3 = Actor(
        movie = "Iron Man 1",
        name = "Jeff",
        surname = "Bridges",
        role = "Obadiah Stane / Iron Monger",
        imageResId = R.drawable.jeff_bridges
    )

    ActorCardsList(actors = listOf(a1, a2, a3))

 */
}

