package com.example.moviezzapp.data.videos.local

import androidx.room.TypeConverter
import com.example.moviezzapp.data.videos.VideoModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class VideosConverter {
    @TypeConverter
    fun toString(array: Array<VideoModel>): String {
        return GsonBuilder().create().toJson(array)
    }
    @TypeConverter
    fun toArray(string: String): Array<VideoModel> {
        return Gson().fromJson(string, (Array<VideoModel>::class.java))
    }
}
