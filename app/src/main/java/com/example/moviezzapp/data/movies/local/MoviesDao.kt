package com.example.moviezzapp.data.movies.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviezzapp.data.movies.MovieModel

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getPagedMovies(): DataSource.Factory<Int, MovieModel>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): MovieModel?

    @Query("SELECT poster_path FROM movies WHERE id = :movieId")
    fun getMovieImageById(movieId: Int): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieModel: MovieModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieModel>)

    @Query("DELETE FROM movies WHERE id = :movieId")
    fun deleteMovieById(movieId: String): Int

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

}
