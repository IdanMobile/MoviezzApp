package com.example.moviezzapp.data.videos.local

import com.example.moviezzapp.data.videos.VideoModel
import com.example.moviezzapp.data.videos.VideosDataSource

class VideosLocalDataSource(
    private val videosDao: VideosDao
) : VideosDataSource.LocalDataSource {

    override fun getVideosByMovieId(id: Int): List<VideoModel>? {
        return videosDao.getVideosByMovieId(id)
    }

    override fun saveVideos(videos: List<VideoModel>) {
        videosDao.insertVideos(videos)
    }

    override fun saveVideo(video: VideoModel) {
        videosDao.insertVideo(video)
    }

    companion object {
        private var INSTANCE: VideosLocalDataSource? = null

        @JvmStatic
        fun getInstance(videosDao: VideosDao): VideosLocalDataSource {
            if (INSTANCE == null) {
                synchronized(VideosLocalDataSource::javaClass) {
                    INSTANCE = VideosLocalDataSource(videosDao)
                }
            }
            return INSTANCE!!
        }
    }
}