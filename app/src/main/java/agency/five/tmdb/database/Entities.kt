package agency.five.tmdb.database

import agency.five.tmdb.Actor
import agency.five.tmdb.PersonFunction
import agency.five.tmdb.ui.components.MovieItemViewState
import androidx.room.*


data class MovieWithActors(
    @Embedded val movie: MovieItemViewState,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "movie_id"
    )
    val actors: List<Actor>
)


@Entity(primaryKeys = ["movie_id", "person_function_id"])
data class MoviePersonsFunctions(
    var movie_id: Int,
    var person_function_id: Int,
    //var department: String
)


data class MovieWithPersonsFunctions(
    @Embedded val movie: MovieItemViewState,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "person_function_id",
        associateBy = Junction(MoviePersonsFunctions::class)
    )
    val personsFunctions: List<PersonFunction>
)