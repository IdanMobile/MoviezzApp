package com.example.moviezzapp.data

import android.util.Log
import com.example.moviezzapp.BuildConfig
import com.example.moviezzapp.data.movies.MovieModel
import com.example.moviezzapp.data.movies.MoviesResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "469061ddfba3ae9b4cbb413630264121"
private const val PATH_MOVIES = "movie/"
//private const val LANGUAGE_KEY = "&language=en-US"
private const val PAGE_KEY = "&page=1"
private const val TOP_RATED_PATH = "${PATH_MOVIES}top_rated"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface MoviesApiService {

    @GET(TOP_RATED_PATH)
    fun getMoviesAsync(@Query("page") page: Int):
            Deferred<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieByIdAsync(@Field("id") id: Int):
            Deferred<MovieModel?>

}

private var mClient: OkHttpClient? = null

//TODO: READ CLIENT NOTE BEFORE PRODUCTION
/**
 * Don't forget to remove Interceptors (or change Logging Level to NONE)
 * in production! Otherwise people will be able to see your request and response on Log Cat.
 */
val client: OkHttpClient
    @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
    get() {
        if (mClient == null) {
            val logsInterceptor = HttpLoggingInterceptor()
            logsInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
            val httpBuilder = OkHttpClient.Builder()
            httpBuilder
//                .connectTimeout(15, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(paramsInterceptor)
                .addInterceptor(logsInterceptor)  /// show all JSON in logCat
            mClient = httpBuilder.build()

        }
        return mClient!!
    }

val paramsInterceptor = Interceptor { chain ->
    Log.d("headersInterceptor", "XXX")

    val original: Request = chain.request()
    val originalHttpUrl: HttpUrl = original.url()

    val url: HttpUrl = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", API_KEY)
        .build()

    val requestBuilder: Request.Builder = original.newBuilder().url(url)
    chain.proceed(requestBuilder.build())
}


object MoviesApi {
    val retrofitService: MoviesApiService by lazy {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .baseUrl(BASE_URL)
            .build()

        retrofit.create(MoviesApiService::class.java)
    }
}