package com.example.moviezzapp.ui.movies.list

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezzapp.R
import com.example.moviezzapp.data.movies.MovieModel
import com.example.moviezzapp.databinding.FragmentMoviesBinding
import com.example.moviezzapp.ui.MainActivity
import com.example.moviezzapp.ui.movies.details.DetailsFragment


/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class MoviesFragment : Fragment() {

    private var firstTime: Boolean = true
//    private lateinit var mScrollListener: MyScrollListener

    private lateinit var mListView: RecyclerView

    /**
     * Lazily initialize our [MoviesViewModel].
     */
    private val mViewModel: MoviesViewModel by lazy {
        ViewModelProviders.of(this).get(MoviesViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the LocationsViewModel
        binding.viewModel = mViewModel

        val orientation = resources.configuration.orientation;
        val count: Int = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

        mListView = binding.moviesListView
        mListView.setHasFixedSize(true)
        mListView.layoutManager = GridLayoutManager(context, count)
        initAdapter()

        (activity as MainActivity).supportActionBar?.run {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }

        return binding.root
    }

    private fun initAdapter() {

        Log.d("initAdapter", "XXX");
        val adapter = MoviesAdapter { movie, imageView ->
            val extras = FragmentNavigatorExtras(
                imageView to "image_view"
            )
            val isTablet = resources.getBoolean(R.bool.isTablet)
            if (isTablet) { // do something
                val fragmentDetails = activity?.supportFragmentManager?.findFragmentById(R.id.details_fragment) as DetailsFragment?
                fragmentDetails?.updateMovie(movie.id)
            } else { // do something else
            Log.d("Movie: ", movie.id.toString())
                findNavController().navigate(R.id.action_details,
                    bundleOf("movie_id" to movie.id),
                    NavOptions.Builder().setLaunchSingleTop(true).build(), // NavOptions
                    extras)
            }

        }


        mListView.adapter = adapter
        mViewModel.movies.observe(
            this,
            Observer<PagedList<MovieModel>> {
                Log.d("moviesLivePagedList", "observe: ${it.size}")
//                Toast.makeText(this.context, "data base has ${it.size} movies", Toast.LENGTH_LONG).show()
                //showEmptyList(it?.size == 0)
                adapter.submitList(it)
                val isTablet = resources.getBoolean(R.bool.isTablet)
                if (isTablet && firstTime) {
                    firstTime = false
                    val fragmentDetails =
                        activity?.supportFragmentManager?.findFragmentById(R.id.details_fragment) as DetailsFragment?
                    if (it.isNotEmpty()) {
                        it[0]?.let { m ->
                            fragmentDetails?.updateMovie(m.id)
                        }
                    }
                }
            }
            //                adapter::submitList
        )
        mViewModel.networkErrors.observe(
            this,
            Observer<String> {
                Toast.makeText(this.context, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
            }

        )
    }
}