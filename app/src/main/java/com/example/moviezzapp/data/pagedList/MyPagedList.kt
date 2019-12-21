package com.example.moviezzapp.data.pagedList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * pass the response interface
 */
class MyPagedList<TModel : Any, TResponse : Any>(
    private val pageSize: Int,
    private val pagedDataSource: DataSource.Factory<Int, TModel>,
    /**
     * returns TResponse and request Boolean isNextPage available
     */
    private val onAsyncSuccess: (repos: TResponse) -> Boolean,
    private val nextPageDeferred: (page: Int) -> Deferred<TResponse>
) {

    // keep the last requested page.
    // When the request is successful, increment the page number.
    private var nextPage = 1

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    // stop requesting on last page
    private var hasNextPage = true

    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    private val networkErrors: LiveData<String>
        get() = _networkErrors

    fun getPagedResult(): PagedResult<TModel> {
        Log.d("getMoviesRemote", "start")

        return PagedResult(
            pagedDataSource,
            pageSize,
            { loadMore() },
            networkErrors
        )
    }

    private fun loadMore() {
        Log.d("MoviesBoundaryCallback", "requestAndSaveData isRequestInProgress: $isRequestInProgress")
        if (isRequestInProgress || !hasNextPage)
            return

        isRequestInProgress = true
        getAsync(
            nextPage,
            onError = { e ->
                _networkErrors.postValue(e)
                isRequestInProgress = false
            },
            onSuccess = { data ->
                hasNextPage = onAsyncSuccess(data)
                nextPage++
                isRequestInProgress = false
            })
    }

    /**
     * Get TResponse list
     * Trigger a request to the RickAndMorty API with the following params:
     * @param page request page index
     *
     * The result of the request is handled by the implementation of the functions passed as params
     * @param onSuccess function that defines how to handle the list of repos received
     * @param onError function that defines how to handle request failure
     */
    private fun getAsync(
        page: Int,
        onSuccess: (repos: TResponse) -> Unit,
        onError: (error: String) -> Unit
    ) {
        Log.d("MoviesApiService", "page: $page")

        GlobalScope.launch(Dispatchers.Default) {

            try {
                //get list from remote
                val response = nextPageDeferred(nextPage).await()
                onSuccess(response)
            } catch (e: Error) {
                onError(e.message ?: "Unknown error")
            }
        }
    }
}
