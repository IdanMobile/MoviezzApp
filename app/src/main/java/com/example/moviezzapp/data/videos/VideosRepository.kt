package com.example.moviezzapp.data.videos

import android.util.Log
import com.example.moviezzapp.data.videos.local.VideosDatabase
import com.example.moviezzapp.data.videos.local.VideosLocalDataSource
import com.example.moviezzapp.data.videos.remote.VideosRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VideosRepository {
    private val videosDatabase: VideosDatabase = VideosDatabase.getDatabase()
    private val localDataSource: VideosLocalDataSource =
        VideosLocalDataSource.getInstance(videosDatabase.videosDao())
    private val remoteDataSource: VideosRemoteDataSource = VideosRemoteDataSource.getInstance()

    fun getVideosByMovieIdAsync(id: Int, videos: (List<VideoModel>) -> Unit) {
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val localVideo = localDataSource.getVideosByMovieId(id) ?: listOf()
                Log.d("getVideoByIdAsync", localVideo.size.toString())

                if (localVideo.isNotEmpty()) {
                    Log.d("getVideoByIdAsync", "return from data base")
                    videos(localVideo)
                } else {
                    val remoteVideos = remoteDataSource.getVideosByMovieIdAsync(id).await()

                    remoteVideos?.results?.let {
                        Log.d("getVideoByIdAsync", "return from server api")
                        videos(it)
                        localDataSource.saveVideos(it)
                    }
                }
            } catch (e: Exception) {
                Log.d("getVideoByIdAsync", "error: ${e.message}")
            }
        }
    }

    companion object {
        private var sINSTANCE: VideosRepository? = null

        fun getInstance(): VideosRepository {
            if (sINSTANCE == null) {
                sINSTANCE = VideosRepository()
            }
            return sINSTANCE!!
        }
    }
}