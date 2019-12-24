package com.example.moviezzapp.data.videos

import kotlinx.coroutines.Deferred

interface VideosDataSource {

    interface LocalDataSource : VideosDataSource {

        fun getVideosByMovieId(id: Int): List<VideoModel>?

        fun saveVideos(videos: List<VideoModel>)

        fun saveVideo(video: VideoModel)

    }

    interface RemoteDataSource : VideosDataSource {
        fun getVideosByMovieIdAsync(id: Int): Deferred<VideoResponse?>
    }
}
