package com.example.moviezzapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviezzapp.R
import com.example.moviezzapp.data.movies.MoviesRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MoviesRepository.getInstance()
    }
}