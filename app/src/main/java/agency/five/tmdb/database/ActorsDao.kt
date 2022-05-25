package agency.five.tmdb.database

import agency.five.tmdb.Actor
import androidx.room.*


@Dao
interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertActors(actors: List<Actor>)

    @Query("DELETE FROM actor WHERE movie_id = :movieId")
    fun deleteActors(movieId: Int)
}