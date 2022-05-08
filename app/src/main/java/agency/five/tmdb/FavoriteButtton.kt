package agency.five.tmdb

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    favorite: Boolean = false
) {
    var isFavorite by remember { mutableStateOf(value = favorite) }

    IconButton(
        onClick = {
            isFavorite = isFavorite.not()
        }
    ) {

        if (isFavorite) {

            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.large_spacing))
                    .background(Color(0x3C0B253F), CircleShape)
                    .padding(dimensionResource(id = R.dimen.micro_spacing)),
                tint = Color.White
            )

        } else {

            Icon(
                Icons.Filled.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.large_spacing))
                    .background(Color(0x3C0B253F), CircleShape)
                    .padding(dimensionResource(id = R.dimen.micro_spacing)),
                tint = Color.White
            )
        }
    }
}


@Preview
@Composable
fun FavoriteButtonPreview() {
    FavoriteButton()
}