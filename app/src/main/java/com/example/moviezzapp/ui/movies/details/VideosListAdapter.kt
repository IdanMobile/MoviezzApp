package com.example.moviezzapp.ui.movies.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezzapp.R
import com.example.moviezzapp.data.videos.VideoModel
import kotlinx.android.synthetic.main.video_item.view.*


class VideoItemListAdapter(private var items: List<VideoModel>, private val onItemClick: (key: String) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.video_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.text_view
    private val imageButton: ImageButton = view.image_button

    fun bind(
        videoModel: VideoModel,
        onItemClick: (String) -> Unit
    ) {
        title.text = videoModel.name
        imageButton.setOnClickListener { onItemClick(videoModel.key) }
    }


}