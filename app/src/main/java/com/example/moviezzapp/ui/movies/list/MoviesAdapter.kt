package com.example.moviezzapp.ui.movies.list

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.request.transition.ViewPropertyTransition
import com.example.moviezzapp.GlideApp
import com.example.moviezzapp.R
import com.example.moviezzapp.data.IMAGE_BASE_URL
import com.example.moviezzapp.data.movies.MovieModel
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.coroutines.*

class MoviesAdapter(private val onItemClick: (MovieModel, ImageView) -> Unit) :
    PagedListAdapter<MovieModel, ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_list_item,
                parent,
                false
            )
        )
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { model ->
            holder.bind(model)
            holder.setOnClickListener {
                onItemClick(model, holder.view.movie_image)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<MovieModel>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(
                old: MovieModel,
                new: MovieModel
            ) = old.id == new.id

            override fun areContentsTheSame(
                old: MovieModel,
                new: MovieModel
            ) = old == new
        }
    }
}

class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private lateinit var model: MovieModel
    private val imageView: ImageView = view.movie_image
    private val scope = CoroutineScope(Dispatchers.Default)

    private val animation: ViewPropertyTransition.Animator = ViewPropertyTransition.Animator { view ->
        val scaleAnimx1 = ObjectAnimator.ofFloat(view, "scaleX", 0.1f, 1f)
        scaleAnimx1.cancel()

        scaleAnimx1.duration = 100
        scaleAnimx1.start()

        val scaleAnimy1 = ObjectAnimator.ofFloat(view, "scaleY", 0.1f, 1f)
        scaleAnimy1.duration = 100
        scaleAnimy1.start()
    }

    fun bind(movie: MovieModel) {
        model = movie

        GlideApp.with(imageView.context)
            .load(IMAGE_BASE_URL + movie.poster_path)
            .transition(GenericTransitionOptions.with(animation))
//            .placeholder()
            .centerCrop()
            .into(imageView)
    }

    private suspend fun typeAnimation(tv: TextView, text: String) {
        var newText = ""
        scope.launch {

            text.map {
                delay(50)
                newText += it
                GlobalScope.launch(Dispatchers.Main) {
                    tv.text = newText
                }
            }
        }.join()
    }

    fun clear() {
        Log.d("ViewHolder", "clear")
    }

    fun setOnClickListener(function: (MovieModel) -> Unit) {
        view.setOnClickListener {

            function(model)
        }
    }
}