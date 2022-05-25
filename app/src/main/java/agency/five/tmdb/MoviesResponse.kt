package agency.five.tmdb

import agency.five.tmdb.ui.components.MovieItemViewState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MoviesResponse(
    @SerialName("results")
    val movies: List<MovieItemViewState>
)
