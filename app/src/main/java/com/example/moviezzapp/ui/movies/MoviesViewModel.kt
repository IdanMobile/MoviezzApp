package com.example.moviezzapp.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviezzapp.data.movies.MovieModel
import com.example.moviezzapp.data.movies.MoviesRepository
import com.example.moviezzapp.data.pagedList.PagedResult

/**
 * The [ViewModel] that is attached to the [MoviesFragment].
 */
class MoviesViewModel : ViewModel() {

    private val repo: MoviesRepository = MoviesRepository.getInstance()

    private val repoResult: PagedResult<MovieModel> = repo.getMovies()

    val movies: LiveData<PagedList<MovieModel>> = repoResult.data
    val networkErrors: LiveData<String> = repoResult.networkErrors

    //TODO: IMPROTANT TO CANCEL ALL VIEW MODEL JOBS !!!
    override fun onCleared() {
        super.onCleared()
    }
}
