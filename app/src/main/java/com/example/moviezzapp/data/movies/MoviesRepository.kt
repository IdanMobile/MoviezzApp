package com.example.moviezzapp.data.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviezzapp.data.movies.local.MoviesDatabase
import com.example.moviezzapp.data.movies.local.MoviesLocalDataSource
import com.example.moviezzapp.data.movies.remote.MoviesRemoteDataSource
import com.example.moviezzapp.data.pagedList.PagedResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviesRepository {
    private val DATABASE_PAGE_SIZE = 50

    // keep the last requested page.
    // When the request is successful, increment the page number.
    private var nextPage = 1

    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    private val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    private val moviesDatabase: MoviesDatabase = MoviesDatabase.getDatabase()
    private val localDataSource: MoviesLocalDataSource =
        MoviesLocalDataSource.getInstance(moviesDatabase.moviesDao())
    private val remoteDataSource: MoviesRemoteDataSource = MoviesRemoteDataSource.getInstance()

    private fun getPagedMovies(): DataSource.Factory<Int, MovieModel> {
        return localDataSource.getPagedMovies()
    }

    fun getMovieImageById(id: Int): String? {
        val image = localDataSource.getMovieImageById(id)
        image?.let {
            return it
        }
        return ""
    }

    fun getMovieById(id: Int, onMovie: (MovieModel) -> Unit) {
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val character = localDataSource.getMovieById(id)
                character?.let {
                    onMovie(it)
                    Log.d("getMovieById", "Success: LocalDB")
                    return@launch
                }

                val remoteMovie = remoteDataSource.getMovieByIdAsync(id).await()
                remoteMovie?.let {
                    onMovie(it)
                    localDataSource.saveMovie(it)
                    Log.d("getMovieById", "Success: ServiceApi")
                }
            } catch (e: Error) {
                Log.d("getMovieById", "error: ${e.message}")
            }
        }
    }

    private fun getMovieByIdAsync(id: Int, character: (MovieModel) -> Unit) {
        GlobalScope.launch(Dispatchers.Default) {
            try {
                Log.d("getMovieByIdAsync", "checking data base")
                val localMovie = localDataSource.getMovieById(id)
                localMovie?.let {
                    Log.d("getMovieByIdAsync", "return from data base")
                    character(it)

                    return@launch
                }

                Log.d("getMovieByIdAsync", "not found in data base, requesting from api")

                val remoteMovie = remoteDataSource.getMovieByIdAsync(id).await()
                remoteMovie?.let {
                    Log.d("getMovieByIdAsync", "return from server api")
                    character(it)
                    localDataSource.saveMovie(it)
                }
            } catch (e: Error) {
                Log.d("getMovieByIdAsync", "error: ${e.message}")
            }
        }
    }

    fun getMovies(): PagedResult<MovieModel> {
        Log.d("getMoviesRemote", "start")

        return PagedResult(
            getPagedMovies(),
            DATABASE_PAGE_SIZE,
            { loadMoreRemoteMovies() },
            networkErrors
        )
    }

    private fun loadMoreRemoteMovies() {
        Log.d("RNMBoundaryCallback", "requestAndSaveData isRequestInProgress: $isRequestInProgress")
        if (isRequestInProgress) return

        isRequestInProgress = true
        getMoviesAsync(
            nextPage,
            onError = { e ->
                _networkErrors.postValue(e)
                isRequestInProgress = false
            },
            onSuccess = { t ->
                localDataSource.saveMovies(t)
                nextPage++
                isRequestInProgress = false
            })
    }

    /**
     * Get characters list
     * Trigger a request to the RickAndMorty API with the following params:
     * @param page request page index
     *
     * The result of the request is handled by the implementation of the functions passed as params
     * @param onSuccess function that defines how to handle the list of repos received
     * @param onError function that defines how to handle request failure
     */
    private fun getMoviesAsync(
        page: Int,
        onSuccess: (repos: List<MovieModel>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        Log.d("MoviesApiService", "page: $page")

        GlobalScope.launch(Dispatchers.Default) {

            try {
                //get list from remote
                val response = remoteDataSource.getMoviesAsync(page).await()
                onSuccess(response.results)
            } catch (e: Error) {
                onError(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        private var sINSTANCE: MoviesRepository? = null

        fun getInstance(): MoviesRepository {
            if (sINSTANCE == null) {
                sINSTANCE = MoviesRepository()
            }
            return sINSTANCE!!
        }
    }
}