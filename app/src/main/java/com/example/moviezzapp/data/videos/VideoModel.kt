package com.example.moviezzapp.data.videos

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoModel (

    @ColumnInfo(name = "movie_id") var movieId: Int = 0,
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "iso_639_1") var iso_639_1 : String,
    @ColumnInfo(name = "iso_3166_1") var iso_3166_1 : String,
    @ColumnInfo(name = "key") var key : String,
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "site") var site : String,
    @ColumnInfo(name = "size") var size : Int,
    @ColumnInfo(name = "type") var type : String
)

data class VideoResponse (
    var id : Int,
    var results : List<VideoModel>
){
    init {
        Log.d("VideoResponse: ", results[0].toString())
        results.map { it.movieId = id }
        Log.d("Results: ", results[0].toString())
    }
}