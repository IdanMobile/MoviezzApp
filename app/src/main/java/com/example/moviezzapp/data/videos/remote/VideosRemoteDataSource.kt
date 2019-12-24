package com.example.moviezzapp.data.videos.remote

import com.example.moviezzapp.data.MoviesApi
import com.example.moviezzapp.data.MoviesApiService
import com.example.moviezzapp.data.videos.VideoResponse
import com.example.moviezzapp.data.videos.VideosDataSource
import kotlinx.coroutines.Deferred


class VideosRemoteDataSource(
    private val rnmServiceApi: MoviesApiService = MoviesApi.retrofitService
) : VideosDataSource.RemoteDataSource {

    override fun getVideosByMovieIdAsync(id: Int): Deferred<VideoResponse?> {
        return rnmServiceApi.getMovieVideoByIdAsync(id)
    }

    companion object {
        private var INSTANCE: VideosRemoteDataSource? = null


        fun getInstance(): VideosRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = VideosRemoteDataSource()
            }
            return INSTANCE!!
        }
    }
}