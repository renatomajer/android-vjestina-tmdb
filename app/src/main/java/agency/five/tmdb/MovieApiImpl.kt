package agency.five.tmdb

import agency.five.tmdb.ui.components.MovieItemViewState
import io.ktor.client.*
import io.ktor.client.request.*


class MovieApiImpl(
    private val client: HttpClient
) : MovieApi {

    private val API_KEY = "0d756c167ac7d8a9afae24e0d3af8a9e"

    override suspend fun getPopularMovies(): List<List<MovieItemViewState>> {   // size: 4 (tabs)

        return try {
            val forRent = getPopularForRent().subList(0, 10)
            val inTheaters = getPopularInTheaters().subList(0, 10)
            val onTV = getPopularOnTV().subList(0, 10)
            val streaming = getPopularStreaming().subList(0, 10)

            //return listOf(l1, l2, l3, l1)
            listOf(streaming, onTV, forRent, inTheaters)

        } catch (exc: Exception) {
            listOf(emptyList(), emptyList(), emptyList(), emptyList())
        }
    }

    override suspend fun getPopularForRent(): List<MovieItemViewState> {
        return try {
            client.get<MoviesResponse>("https://api.themoviedb.org/3/movie/popular?api_key=$API_KEY").movies
        } catch (exc: Exception) {
            emptyList()
        }
    }

    override suspend fun getPopularInTheaters(): List<MovieItemViewState> {
        return try {
            client.get<MoviesResponse>("https://api.themoviedb.org/3/movie/now_playing?api_key=$API_KEY").movies
        } catch (exc: Exception) {
            emptyList()
        }
    }

    override suspend fun getPopularStreaming(): List<MovieItemViewState> {
        // now playing movies on page=2
        return try {
            client.get<MoviesResponse>("https://api.themoviedb.org/3/movie/now_playing?api_key=$API_KEY&page=2").movies
        } catch (exc: Exception) {
            emptyList()
        }
    }

    override suspend fun getPopularOnTV(): List<MovieItemViewState> {
        // popular movies on page=2
        return try {
            client.get<MoviesResponse>("https://api.themoviedb.org/3/movie/popular?api_key=$API_KEY&page=2").movies
        } catch (exc: Exception) {
            emptyList()
        }
    }

    override suspend fun getTrendingMovies(): List<List<MovieItemViewState>> {  // size: 2 (tabs)
        return try {
            listOf(getTrendingToday().subList(0, 10), getTrendingThisWeek().subList(0, 10))
        } catch (exc: Exception) {
            listOf(emptyList(), emptyList())
        }
    }

    override suspend fun getTrendingToday(): List<MovieItemViewState> {
        return try {
            client.get<MoviesResponse>("https://api.themoviedb.org/3/trending/movie/day?api_key=$API_KEY").movies
        } catch (exc: Exception) {
            emptyList()
        }
    }

    override suspend fun getTrendingThisWeek(): List<MovieItemViewState> {
        return try {
            client.get<MoviesResponse>("https://api.themoviedb.org/3/trending/movie/week?api_key=$API_KEY").movies
        } catch (exc: Exception) {
            emptyList()
        }
    }

    override suspend fun getFreeMovies(): List<List<MovieItemViewState>> {  // size: 2 (tabs)
        return try {
            listOf(getUpcomingMovies().subList(0, 10), getTopRatedMovies().subList(0, 10))
        } catch (exc: Exception) {
            listOf(emptyList(), emptyList())
        }
    }

    override suspend fun getTopRatedMovies(): List<MovieItemViewState> {
        return try {
            client.get<MoviesResponse>("https://api.themoviedb.org/3/movie/top_rated?api_key=$API_KEY").movies
        } catch (exc: Exception) {
            emptyList()
        }
    }

    override suspend fun getUpcomingMovies(): List<MovieItemViewState> {
        return try {
            client.get<MoviesResponse>("https://api.themoviedb.org/3/movie/upcoming?api_key=$API_KEY").movies
        } catch (exc: Exception) {
            emptyList()
        }
    }

    override suspend fun getMovie(movieId: Int): MovieItemViewState {
        return try {
            client.get<MovieItemViewState>("https://api.themoviedb.org/3/movie/$movieId?api_key=$API_KEY")
        } catch (exc: Exception) {
            MovieItemViewState()
        }
    }

    override suspend fun getPersonFunctions(movieId: Int): List<PersonFunction> {
        return try {
            val result =
                client.get<PersonsFunctionsResponse>("https://api.themoviedb.org/3/movie/$movieId/credits?api_key=$API_KEY").personsFunctions
            //result.forEach { it.movie_id = movieId } // set each persons movie id

            if (result.size > 6) result.subList(0, 6)
            else result
        } catch (exc: Exception) {
            emptyList()
        }
    }

    override suspend fun getActors(movieId: Int): List<Actor> {
        return try {
            val result =
                client.get<ActorResponse>("https://api.themoviedb.org/3/movie/$movieId/credits?api_key=$API_KEY").actors
            result.forEach { it.movie_id = movieId } // set each actors movie id

            if (result.size > 6) result.subList(0, 6)
            else result
        } catch (exc: Exception) {
            emptyList()
        }
    }
}