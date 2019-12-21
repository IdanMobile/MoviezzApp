package com.example.moviezzapp.data.pagedList

import android.util.Log
import androidx.paging.PagedList

class MovieBoundaryCallback<T : Any>(
    private val loadMore: () -> Unit
) : PagedList.BoundaryCallback<T>() {

    override fun onZeroItemsLoaded() {
        Log.d("MovieBoundaryCallback", "onZeroItemsLoaded")
        loadMore()
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        Log.d("MovieBoundaryCallback", "onItemAtEndLoaded")
        loadMore()
    }
}