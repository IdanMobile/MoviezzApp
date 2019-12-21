package com.example.moviezzapp.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezzapp.data.movies.MovieModel
import com.example.moviezzapp.databinding.FragmentMoviesBinding
import com.example.moviezzapp.ui.movies.list.MoviesAdapter


/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class MoviesFragment : Fragment() {

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

        mListView = binding.moviesListView
        mListView.layoutManager = GridLayoutManager(context, 2)
        initAdapter()

        return binding.root
    }

    private fun initAdapter() {
        val adapter = MoviesAdapter() {
//            context?.let { context ->
//                showDetails(
//                    context,
//                    it.id,
//                    DetailsTypes.TYPE_CHARACTER
//                )
//            }
        }
        mListView.adapter = adapter
        mViewModel.movies.observe(
            this,
            Observer<PagedList<MovieModel>> {
                Log.d("moviesLivePagedList", "observe: ${it.size}")
                Toast.makeText(this.context, "data base has ${it.size} movies", Toast.LENGTH_LONG).show()
                //showEmptyList(it?.size == 0)
                adapter.submitList(it)
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