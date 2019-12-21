package com.example.moviezzapp.data.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieModel(
    @PrimaryKey
    /** Movie Primary id **/
    @ColumnInfo(name = "id") var id: Float,
    /** Movie Popularity Rating **/
    @ColumnInfo(name = "popularity") var popularity: Float,
    @ColumnInfo(name = "vote_count") var vote_count: Float,
    @ColumnInfo(name = "video") var video: Boolean,
    @ColumnInfo(name = "poster_path") var poster_path: String,
    @ColumnInfo(name = "adult") var adult: Boolean,
    @ColumnInfo(name = "backdrop_path") var backdrop_path: String,
    @ColumnInfo(name = "original_language") var original_language: String,
    @ColumnInfo(name = "original_title") var original_title: String,
    @ColumnInfo(name = "genre_ids") var genre_ids: Array<Int>,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "vote_average") var vote_average: Float,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "release_date") var release_date: String
)


/** Top Rated movies atm **/
data class MoviesResponse(
    var page: Int,
    var total_results: Float,
    var total_pages: Float,
    var results: List<MovieModel>
)