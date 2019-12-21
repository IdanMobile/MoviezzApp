package com.example.moviezzapp.data.movies.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviezzapp.MoviezzApplication
import com.example.moviezzapp.data.movies.MovieModel

@Database(entities = [MovieModel::class], version = 1, exportSchema = false)
@TypeConverters(MoviesConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        private val lock = Any()

        fun getDatabase(): MoviesDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        MoviezzApplication.getInstance().applicationContext,
                        MoviesDatabase::class.java, "movies")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}