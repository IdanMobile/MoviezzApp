package com.example.moviezzapp.data.videos.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviezzapp.data.videos.VideoModel

@Dao
interface VideosDao {

    @Query("SELECT * FROM videos WHERE movie_id = :videoId")
    fun getVideosByMovieId(videoId: Int): List<VideoModel>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(videoModel: VideoModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideos(videos: List<VideoModel>)

}
