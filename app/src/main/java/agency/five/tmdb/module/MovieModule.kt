package agency.five.tmdb.module


import agency.five.tmdb.*
import agency.five.tmdb.database.AppDatabase
import agency.five.tmdb.ui.viewmodel.DetailsScreenViewModel
import agency.five.tmdb.ui.viewmodel.FavoritesScreenViewModel
import agency.five.tmdb.ui.viewmodel.HomeScreenViewModel
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieModule = module {

    single<HttpClient> {
        HttpClient(Android) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HTTP", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    single<AppDatabase> {
        AppDatabase.getInstance(androidApplication())
    }

    single<MovieApi> { MovieApiImpl(client = get()) }
    single<MovieDatabase> { MovieDatabaseImpl() }

    single<MovieRepository> {
        MovieRepositoryImpl(
            movieApi = get(),
            movieDatabase = get(),
            db = get()
        )
    }

    viewModel {
        FavoritesScreenViewModel()
    }

    viewModel { params ->
        DetailsScreenViewModel(id = params.get())
    }

    viewModel {
        HomeScreenViewModel()
    }
}