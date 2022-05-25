package agency.five.tmdb

import agency.five.tmdb.database.AppDatabase
import agency.five.tmdb.database.MoviePersonsFunctions
import agency.five.tmdb.ui.components.MovieItemViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase,
    private val db: AppDatabase
) : MovieRepository {

    private val actorsDao = db.actorsDao()
    private val movieDao = db.movieDao()
    private val moviePersonsFunctionsDao = db.moviePersonsFunctionsDao()
    private val personsFunctionsDao = db.personFunctionsDao()

    private val favorites: Flow<List<MovieItemViewState>> = movieDao.getAll().shareIn(
        CoroutineScope(Dispatchers.Default),
        SharingStarted.WhileSubscribed(),
        replay = 1
    )

    private object RefreshEvent

    private val refreshMoviesPublisher = MutableSharedFlow<RefreshEvent>(replay = 1)

    private val popularMoviesInitialFlow = flow {
        //Log.d("debug_log", "INITIAL FLOW EMITED")
        emit(movieApi.getPopularMovies())
    }.shareIn(
        CoroutineScope(Dispatchers.Default),
        SharingStarted.WhileSubscribed(),
        replay = 1
    )
//
//    private val popularMoviesFlow = refreshMoviesPublisher
//        .map {
//            //Log.d("debug_log", "REFRESH EVENT POPULAR")
//            movieApi.getPopularMovies()
//        }
//        .shareIn(
//            CoroutineScope(Dispatchers.Default),
//            SharingStarted.WhileSubscribed(),
//            replay = 1
//        )


    private val popularMoviesFlow = favorites
        .map {
            //Log.d("debug_log", "REFRESH EVENT POPULAR")
            movieApi.getPopularMovies()
        }
        .shareIn(
            CoroutineScope(Dispatchers.Default),
            SharingStarted.WhileSubscribed(),
            replay = 1
        )


    override fun getPopularMovies(): Flow<List<List<MovieItemViewState>>> = merge(
        popularMoviesInitialFlow,
        popularMoviesFlow
    ).onEach { it ->
        var favs = movieDao.getAll().first()
        if (favs.isNotEmpty()) {
            it.forEach { list ->
                list.forEach {
                    var isFavorite = false
                    for (fav in favs) {
                        if (fav.id == it.id) {
                            isFavorite = true
                        }
                    }
                    it.favorite = isFavorite
                }
            }
        }
    }

    private val trendingMoviesInitialFlow = flow {
        emit(movieApi.getTrendingMovies())
    }
        .shareIn(
            CoroutineScope(Dispatchers.Default),
            SharingStarted.WhileSubscribed(),
            replay = 1
        )

//    private val trendingMoviesFlow = refreshMoviesPublisher
//        .map {
//            //Log.d("debug_log", "REFRESH EVENT TRENDING")
//            movieApi.getTrendingMovies()
//        }
//        .shareIn(
//            CoroutineScope(Dispatchers.Default),
//            SharingStarted.WhileSubscribed(),
//            replay = 1
//        )

    private val trendingMoviesFlow = favorites
        .map {
            //Log.d("debug_log", "REFRESH EVENT TRENDING")
            movieApi.getTrendingMovies()
        }
        .shareIn(
            CoroutineScope(Dispatchers.Default),
            SharingStarted.WhileSubscribed(),
            replay = 1
        )

    override fun getTrendingMovies(): Flow<List<List<MovieItemViewState>>> = merge(
        trendingMoviesInitialFlow,
        trendingMoviesFlow
    ).onEach { it ->
        var favs = movieDao.getAll().first()
        if (favs.isNotEmpty()) {
            it.forEach { list ->
                list.forEach {
                    var isFavorite = false
                    for (fav in favs) {
                        if (fav.id == it.id) {
                            isFavorite = true
                        }
                    }
                    it.favorite = isFavorite
                }
            }
        }
    }

    private val freeMoviesInitialFlow = flow {
        emit(movieApi.getFreeMovies())
    }
        .shareIn(
            CoroutineScope(Dispatchers.Default),
            SharingStarted.WhileSubscribed(),
            replay = 1
        )

//    private val freeMoviesFlow = refreshMoviesPublisher
//        .map {
//            //Log.d("debug_log", "REFRESH EVENT FREE")
//            movieApi.getFreeMovies()
//        }
//        .shareIn(
//            CoroutineScope(Dispatchers.Default),
//            SharingStarted.WhileSubscribed(),
//            replay = 1
//        )

    private val freeMoviesFlow = favorites
        .map {
            //Log.d("debug_log", "REFRESH EVENT FREE")
            movieApi.getFreeMovies()
        }
        .shareIn(
            CoroutineScope(Dispatchers.Default),
            SharingStarted.WhileSubscribed(),
            replay = 1
        )

    override fun getFreeMovies(): Flow<List<List<MovieItemViewState>>> = merge(
        freeMoviesInitialFlow,
        freeMoviesFlow
    ).onEach { it ->
        var favs = movieDao.getAll().first()
        if (favs.isNotEmpty()) {
            it.forEach { list ->
                list.forEach {
                    var isFavorite = false
                    for (fav in favs) {
                        if (fav.id == it.id) {
                            isFavorite = true
                        }
                    }
                    it.favorite = isFavorite
                }
            }
        }
    }

    override fun getMovie(movieId: Int): Flow<MovieItemViewState> {
        return flow {
            var movie = movieApi.getMovie(movieId)
            if (movie.id == -1) { // if there is no connection movieApi returns a movie with id = -1
                // find the movie with the given id in the database and emit it
                movie = movieDao.getAll().first().first { it.id == movieId }
            }
            emit(movie)
        }
    }

    override fun getPersonFunctions(movieId: Int): Flow<List<PersonFunction>> {
        return flow {
            var personsFunctions = movieApi.getPersonFunctions(movieId)

            if (personsFunctions.isEmpty()) {
                personsFunctions = moviePersonsFunctionsDao.getMovieWithPersonsFunctions(movieId)
                    .first().personsFunctions
            }
            emit(personsFunctions)
        }
    }

    override fun getActors(movieId: Int): Flow<List<Actor>> {
        return flow {
            var actors = movieApi.getActors(movieId)

            if (actors.isEmpty()) {
                actors = movieDao.getMovieWithActors(movieId).first().actors
            }

            emit(actors)
        }
    }

//    private val favouriteMoviesFlow = refreshMoviesPublisher
//        .map {
//            movieDatabase.getFavoriteMovies()
//        }
//        .shareIn(
//            CoroutineScope(Dispatchers.Default),
//            SharingStarted.WhileSubscribed(),
//            replay = 1
//        )

    //override fun getFavorites(): Flow<List<MovieItemViewState>> = favouriteMoviesFlow
    override fun getFavorites(): Flow<List<MovieItemViewState>> = favorites

    override suspend fun markMovieFavorite(movie: MovieItemViewState, isFavorite: Boolean) {
        movie.favorite = isFavorite

        if (movie.favorite) {
            movie.runtime = movieApi.getMovie(movie.id).runtime // set movie runtime
            movieDao.insertMovie(movie)

            val actors = movieApi.getActors(movie.id)
            actorsDao.insertActors(actors) // Insert actor related to that movie

            val persons = movieApi.getPersonFunctions(movie.id)
            personsFunctionsDao.insertPersonsFunctions(persons)

            for (person in persons) {
                moviePersonsFunctionsDao.insertMoviePersonsFunctions(
                    MoviePersonsFunctions(
                        movie.id,
                        person.id
                    )
                )
            }


        } else {
            movieDao.deleteMovie(movie)
            actorsDao.deleteActors(movie.id)

            moviePersonsFunctionsDao.deleteMoviePersonsFunctions(movie.id)

            if (movieDao.getAll().first().isEmpty()) {
                // if there is no more movies in favorites, remove all of the remaining data
                personsFunctionsDao.deleteAll()
            }

        }

        //refreshMoviesPublisher.emit(RefreshEvent)
    }
}