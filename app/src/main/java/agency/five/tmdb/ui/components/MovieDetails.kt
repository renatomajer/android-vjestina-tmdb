package agency.five.tmdb.ui.components

import agency.five.tmdb.R
import agency.five.tmdb.ui.theme.Typography
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter


@Composable
fun MovieDetails(
    modifier: Modifier = Modifier,
    item: MovieItemViewState
) {
    Box(
        modifier = modifier
            .height(dimensionResource(id = R.dimen.details_movie_image_height))
            .width(dimensionResource(id = R.dimen.details_movie_image_width))
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w400" + item.poster_path),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopStart
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 50f
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.home_movies_list_content_padding)),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    CircularProgresBar(
                        value = (item.vote_average * 10).toInt(),
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.small_spacing))
                    )

                    Text(
                        text = stringResource(id = R.string.user_score),
                        style = Typography.subtitle2,
                        color = Color.White
                    )
                }

                Text(
                    text = item.title,
                    style = Typography.h5
                )

                Text(
                    text = item.release_date + " (" + item.original_language + ")",
                    style = Typography.body2,
                    color = Color.White
                )

                Row {
                    for (i in item.genres.indices) {

                        if (i == 0) {
                            Text(
                                text = item.genres[i].name,
                                style = Typography.body2,
                                color = Color.White
                            )
                        } else {
                            Text(
                                text = ", " + item.genres[i].name,
                                style = Typography.body2,
                                color = Color.White
                            )
                        }
                    }

                    Text(
                        text = " " + item.runtime + "m",
                        style = Typography.body2,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                StarButton()
            }
        }
    }
}


@Composable
fun StarButton() {
    var checked by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            checked = checked.not()
        },
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.micro_spacing))
    ) {
        if (!checked) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star_border),
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.large_spacing))
                    .background(Color(0x3C757575), CircleShape)
                    .padding(dimensionResource(id = R.dimen.micro_spacing)),
                tint = Color.White
            )
        } else {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.large_spacing))
                    .background(Color(0x3C757575), CircleShape)
                    .padding(dimensionResource(id = R.dimen.micro_spacing))
            )
        }
    }
}


@Preview
@Composable
fun StarButtonPreview() {
    StarButton()
}


@Preview
@Composable
fun MovieDetailsPreview() {
    /*
    val item = MovieItemViewState(
        id = 0,
        title = "Iron Man 1",
        overview = "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.",
        imageResId = R.drawable.iron_man_1_medium,
        release_date = "05/02/2008",
        vote_average = 7.6,
        original_language = "US",
        duration = "2h 6m",
        genres = mutableListOf("Action", "Science Fiction", "Adventure")
    )

    MovieDetails(item = item)

     */
}


@Composable
fun CircularProgresBar(
    value: Int = 0,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(dimensionResource(id = R.dimen.circular_progress_bar))
    ) {
        CircularProgressIndicator(
            progress = 1f,
            color = Color(0x3219F57D),
            strokeWidth = dimensionResource(id = R.dimen.circular_progress_bar_stroke_width)
        )

        CircularProgressIndicator(
            progress = value / 100f,
            color = Color(0xFF19F57D),
            strokeWidth = dimensionResource(id = R.dimen.circular_progress_bar_stroke_width)
        )

        Text(
            text = value.toString() + "%",
            style = Typography.subtitle2,
            color = Color.White,
            fontSize = 9.sp
        )
    }
}


@Preview
@Composable
fun CircularProgrssIndicatorPreview() {
    CircularProgresBar(3)
}