package com.example.moviezzapp.data.movies.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MoviesConverter {
    @TypeConverter
    fun toString(array: Array<Int>): String {
        return GsonBuilder().create().toJson(array)
    }
    @TypeConverter
    fun toArray(string: String): Array<Int> {
        return Gson().fromJson(string, (Array<Int>::class.java))
    }

//    @TypeConverter
//    fun toString(array: Array<Int>): String {
//        return GsonBuilder().create().toJson(array)
//    }
//    @TypeConverter
//    fun toArray(string: String): Array<Int> {
//        return Gson().fromJson(string, (Array<Int>::class.java))
//    }

//    @TypeConverter
//    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

}