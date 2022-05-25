package agency.five.tmdb.database

import agency.five.tmdb.ui.components.MovieItemViewState
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieItemViewState")
    fun getAll(): Flow<List<MovieItemViewState>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: MovieItemViewState)

    @Delete
    fun deleteMovie(movie: MovieItemViewState)

    @Transaction
    @Query("SELECT * FROM MovieItemViewState WHERE movie_id = :movieId")
    fun getMovieWithActors(movieId: Int): Flow<MovieWithActors>
}