package agency.five.tmdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity(primaryKeys = ["actor_id", "movie_id"])
@Serializable
data class Actor(
//    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "actor_id")
    var id: Int,
    var name: String,
    var movie_id: Int = 0,
    var character: String = "", // needs to be changed
    var profile_path: String?
)


@Serializable
data class ActorResponse(
    @SerialName("cast")
    val actors: List<Actor>
)


@Entity
@Serializable
data class PersonFunction(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "person_function_id")
    var id: Int,
    var name: String,
    var department: String,
    //var movie_id: Int = 0
)


@Serializable
data class PersonsFunctionsResponse(
    @SerialName("crew")
    val personsFunctions: List<PersonFunction>
)