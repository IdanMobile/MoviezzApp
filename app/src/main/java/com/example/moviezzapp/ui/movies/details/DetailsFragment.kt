package com.example.moviezzapp.ui.movies.details

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.moviezzapp.GlideApp
import com.example.moviezzapp.R
import com.example.moviezzapp.data.IMAGE_BASE_URL
import com.example.moviezzapp.data.movies.MovieModel
import com.example.moviezzapp.data.videos.VideoModel
import com.example.moviezzapp.databinding.FragmentDetailsBinding
import com.example.moviezzapp.ui.MainActivity


class DetailsFragment : Fragment() {

    /**
     * Lazily initialize our [DetailsViewModel].
     */
    private val mViewModel: DetailsViewModel by lazy {
        ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater)
        setHasOptionsMenu(true)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the LocationsViewModel
        binding.viewModel = mViewModel

        if (arguments?.containsKey("movie_id") == true) {
            arguments?.getInt("movie_id" )?.let {
                Log.d("XXXid: ", it.toString())
                    updateMovie(it)
            }

        }

        mViewModel.movie.observe(this,
            Observer<MovieModel> {
                initMovieData(binding, it)
            }
        )
        mViewModel.videos.observe(this,
            Observer<List<VideoModel>> {
                initVideoButtons(binding.videosList, it)
            }
        )

        val isTablet = resources.getBoolean(R.bool.isTablet)
        if (!isTablet) {
            (activity as MainActivity).supportActionBar?.run {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
        return binding.root
    }

    fun updateMovie(id: Int) {
        mViewModel.getMovie(id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initVideoButtons(list: RecyclerView, videos: List<VideoModel>) {
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = VideoItemListAdapter(videos) {
            //on video clicked
            showTrailer(it)
        }

    }


    private fun initMovieData(view: FragmentDetailsBinding, movie: MovieModel) {
        Log.d("Observer: ", movie.toString())
        GlideApp.with(this)
            .load(IMAGE_BASE_URL + movie.poster_path)
            .centerCrop()
            .into(view.imageView)

        view.tvDescription.text = movie.overview
        view.tvDescription.movementMethod = ScrollingMovementMethod()
        view.tvReleaseDate.text = movie.release_date
        view.tvTitle.text = movie.title
        view.ratingBar.rating = movie.vote_average/2

    }

    fun showTrailer(uri: String){
        val webIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${uri}"))
        try {
            startActivity(webIntent)
        } catch (ex: ActivityNotFoundException) {
        }
    }
}
