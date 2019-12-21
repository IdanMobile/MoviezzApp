package com.example.moviezzapp.data.movies.remote

import com.example.moviezzapp.data.MoviesApi
import com.example.moviezzapp.data.MoviesApiService
import com.example.moviezzapp.data.movies.MovieModel
import com.example.moviezzapp.data.movies.MoviesDataSource
import com.example.moviezzapp.data.movies.MoviesResponse
import kotlinx.coroutines.Deferred


class MoviesRemoteDataSource(
    private val rnmServiceApi: MoviesApiService = MoviesApi.retrofitService
) : MoviesDataSource.RemoteDataSource {

    override fun getMoviesAsync(page: Int): Deferred<MoviesResponse> {
        return rnmServiceApi.getMoviesAsync(page)
    }

    override fun getMovieByIdAsync(id: Int): Deferred<MovieModel?> {
        return rnmServiceApi.getMovieByIdAsync(id)
    }

    companion object {
        private var INSTANCE: MoviesRemoteDataSource? = null


        fun getInstance(): MoviesRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = MoviesRemoteDataSource()
            }
            return INSTANCE!!
        }
    }
}