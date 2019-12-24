package com.example.moviezzapp.ui.movies.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviezzapp.data.movies.MovieModel
import com.example.moviezzapp.data.movies.MoviesRepository
import com.example.moviezzapp.data.videos.VideoModel
import com.example.moviezzapp.data.videos.VideosRepository

class DetailsViewModel : ViewModel() {

    private val moviesRepo: MoviesRepository = MoviesRepository.getInstance()
    private val videosRepo: VideosRepository = VideosRepository.getInstance()

     val movie: MutableLiveData<MovieModel> by lazy {
        MutableLiveData<MovieModel>()
    }
    val videos: MutableLiveData<List<VideoModel>> by lazy {
        MutableLiveData<List<VideoModel>>()
    }
    // LiveData of network errors.

    fun getMovie(movieId: Int){
        moviesRepo.getMovieById(movieId) {
            Log.d("getMovie: ", it.toString())
            movie.postValue(it)
        }
        videosRepo.getVideosByMovieIdAsync(movieId) {
            videos.postValue(it)
        }
    }

    //TODO: IMPROTANT TO CANCEL ALL VIEW MODEL JOBS !!!
    override fun onCleared() {
        super.onCleared()
    }
}