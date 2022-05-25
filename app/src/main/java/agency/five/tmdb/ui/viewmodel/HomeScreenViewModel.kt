package agency.five.tmdb.ui.viewmodel

import agency.five.tmdb.MovieRepository
import agency.five.tmdb.ui.components.MovieItemViewState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject


class HomeScreenViewModel() : ViewModel(), KoinComponent {

    private val movieRepository: MovieRepository by inject()

    private val popularMoviesLists: Flow<List<List<MovieItemViewState>>> = loadPopularMoviesLists()
    private val freeMoviesLists: Flow<List<List<MovieItemViewState>>> = loadFreeMoviesLists()
    private val trendingMoviesLists: Flow<List<List<MovieItemViewState>>> =
        loadTrendingMoviesLists()

    fun getPopularMoviesLists(): Flow<List<List<MovieItemViewState>>> {  // returns a list of a list of movies for particular section
        return popularMoviesLists
    }

    fun getFreeMoviesLists(): Flow<List<List<MovieItemViewState>>> {  // returns a list of a list of movies for particular section
        return freeMoviesLists
    }

    fun getTrendingMoviesLists(): Flow<List<List<MovieItemViewState>>> {  // returns a list of a list of movies for particular section
        return trendingMoviesLists
    }

    private fun loadPopularMoviesLists(): Flow<List<List<MovieItemViewState>>> {
        return movieRepository.getPopularMovies()
    }

    private fun loadFreeMoviesLists(): Flow<List<List<MovieItemViewState>>> {
        return movieRepository.getFreeMovies()
    }

    private fun loadTrendingMoviesLists(): Flow<List<List<MovieItemViewState>>> {
        return movieRepository.getTrendingMovies()
    }

    fun movieFavoriteButtonClick(movie: MovieItemViewState, isFavorite: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            movieRepository.markMovieFavorite(movie, isFavorite)
        }
    }
}