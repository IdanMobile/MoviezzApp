package com.example.moviezzapp

import androidx.multidex.MultiDexApplication

class MoviezzApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }

    companion object {
        private var sInstance: MoviezzApplication =
            MoviezzApplication()

        @Synchronized
        fun getInstance(): MoviezzApplication {
            return sInstance
        }
    }
}