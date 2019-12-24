package com.example.moviezzapp.data.videos.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviezzapp.MoviezzApplication
import com.example.moviezzapp.data.videos.VideoModel


@Database(entities = [VideoModel::class], version = 1, exportSchema = false)
@TypeConverters(VideosConverter::class)
abstract class VideosDatabase : RoomDatabase() {

    abstract fun videosDao(): VideosDao

    companion object {
        @Volatile
        private var INSTANCE: VideosDatabase? = null

        private val lock = Any()

        fun getDatabase(): VideosDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        MoviezzApplication.getInstance().applicationContext,
                        VideosDatabase::class.java, "videos")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}