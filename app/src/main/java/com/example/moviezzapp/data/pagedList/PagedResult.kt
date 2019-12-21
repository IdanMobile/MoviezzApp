package com.example.moviezzapp.data.pagedList

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

/**
 * PagedResult
 * which contains LiveData<List<CharacterModel>> holding query data,
 * and a LiveData<String> of network error state.
 */
data class PagedResult<T : Any>(
    private val dataSourceFactory: DataSource.Factory<Int, T>,
    private val DATABASE_PAGE_SIZE: Int,
    private val loadMore: () -> Unit,
    val networkErrors: LiveData<String>
) {
    var data: LiveData<PagedList<T>> = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
        .setBoundaryCallback(MovieBoundaryCallback<T> { loadMore() })
        .build()
}
