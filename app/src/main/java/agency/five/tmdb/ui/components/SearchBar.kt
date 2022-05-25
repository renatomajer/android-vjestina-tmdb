package agency.five.tmdb.ui.components

import agency.five.tmdb.R
import agency.five.tmdb.ui.theme.Typography
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// TODO: modify search bar behaviour
@Composable
fun SearchBar() {
    var text by remember { mutableStateOf(value = "") }
    var showClearIcon by remember { mutableStateOf(false) }

    if (text.isEmpty()) {
        showClearIcon = false
    } else if (text.isNotEmpty()) {
        showClearIcon = true
    }

    TextField(
        value = text,
        onValueChange = { text = it },
        singleLine = true,
        modifier = Modifier
            .padding(
                top = dimensionResource(id = R.dimen.search_bar_top_bottom_padding),
                bottom = dimensionResource(id = R.dimen.search_bar_top_bottom_padding)
            )
            .background(color = Color(0xFFEAEAEB), shape = RoundedCornerShape(12.dp))
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                Icons.Rounded.Search,
                contentDescription = "Search Icon",
                modifier = Modifier.size(size = 20.dp),
                tint = Color(0xFF0B253F)
            )
        },
        textStyle = Typography.body1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), //new
        trailingIcon = {
            if (showClearIcon) {
                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = Color(0xFF0B253F),
                        contentDescription = "Clear Icon"
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            cursorColor = Color(0xFF0B253F),
            unfocusedLabelColor = Color.Transparent
        ),
        label = {
            Text(
                text = stringResource(id = R.string.search),
                style = Typography.body1,
                color = Color(0x320B253F)
            )
        }
    )
}


@Preview
@Composable
fun SearchBarPreview() {
    SearchBar()
}