package com.example.moviezzapp.data.movies

import androidx.paging.DataSource
import kotlinx.coroutines.Deferred

interface MoviesDataSource {

    interface LocalDataSource : MoviesDataSource {
        fun getMovieImageById(id: Int): String?

        fun getMovieById(id: Int): MovieModel?

        fun saveMovies(movies: List<MovieModel>)

        fun saveMovie(movie: MovieModel)

        fun getPagedMovies(): DataSource.Factory<Int, MovieModel>
    }

    interface RemoteDataSource : MoviesDataSource {
        fun getMovieByIdAsync(id: Int): Deferred<MovieModel?>

        fun getMoviesAsync(page: Int): Deferred<MoviesResponse>

    }
}
