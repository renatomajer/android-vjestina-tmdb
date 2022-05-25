package agency.five.tmdb.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviePersonsFunctionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMoviePersonsFunctions(mpf: MoviePersonsFunctions)

    @Query("DELETE FROM MoviePersonsFunctions WHERE movie_id = :movieId")
    fun deleteMoviePersonsFunctions(movieId: Int)

    @Transaction
    @Query("SELECT * FROM MovieItemViewState WHERE movie_id = :movieId")
    fun getMovieWithPersonsFunctions(movieId: Int): Flow<MovieWithPersonsFunctions>
}