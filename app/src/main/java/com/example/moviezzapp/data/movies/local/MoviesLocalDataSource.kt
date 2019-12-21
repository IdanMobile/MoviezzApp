package com.example.moviezzapp.data.movies.local

import androidx.paging.DataSource
import com.example.moviezzapp.data.movies.MovieModel
import com.example.moviezzapp.data.movies.MoviesDataSource

class MoviesLocalDataSource(
    private val moviesDao: MoviesDao
) : MoviesDataSource.LocalDataSource {

    override fun getPagedMovies(): DataSource.Factory<Int, MovieModel> {
        return moviesDao.getPagedMovies()
    }

    override fun saveMovies(movies: List<MovieModel>) {
        moviesDao.insertMovies(movies)
    }

    override fun getMovieImageById(id: Int): String? {
        return moviesDao.getMovieImageById(id)
    }

    override fun getMovieById(id: Int): MovieModel? {
        return moviesDao.getMovieById(id)
    }

    override fun saveMovie(movie: MovieModel) {
        moviesDao.insertMovie(movie)
    }

    companion object {
        private var INSTANCE: MoviesLocalDataSource? = null

        @JvmStatic
        fun getInstance(moviesDao: MoviesDao): MoviesLocalDataSource {
            if (INSTANCE == null) {
                synchronized(MoviesLocalDataSource::javaClass) {
                    INSTANCE = MoviesLocalDataSource(moviesDao)
                }
            }
            return INSTANCE!!
        }
    }
}