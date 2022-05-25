package agency.five.tmdb.database

import agency.five.tmdb.PersonFunction
import androidx.room.*


@Dao
interface PersonsFunctionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPersonsFunctions(personsFunctions: List<PersonFunction>)

    @Query("DELETE FROM personfunction")
    fun deleteAll()
}