//package com.example.moviezzapp.ui.movies.details
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat.startActivity
//import androidx.recyclerview.widget.RecyclerView
//
//import com.example.moviezzapp.R
//
//enum class DetailsTypes {
//    TYPE_CHARACTER,
//    TYPE_EPISODE,
//    TYPE_LOCATION
//}
//
//fun showDetails(context: Context, id: Int, type: DetailsTypes) {
//    val intent = Intent(context, DetailsActivity::class.java)
//    intent.putExtra("id", id)
//    intent.putExtra("type", type)
//    startActivity(context, intent, null);
//}
//
//class DetailsActivity : AppCompatActivity() {
//
//    /**
//     * Our MainActivity is only responsible for setting the content view that contains the
//     * Navigation Host.
//     */
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val id = intent.getIntExtra("id", 0)
//        val type: DetailsTypes = intent.getSerializableExtra("type") as DetailsTypes
//
//        setContentView(R.layout.activity_details_location)
//    }
//}
//
//class MiniEpisodesAdapter(private val items: List<String>) :
//    RecyclerView.Adapter<MiniEpisodesAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.text_list_item, parent, false)
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(items[position])
//    }
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val tv: TextView = view.tv_text
//
//        fun bind(s: String) {
//            tv.text = s
//        }
//    }
//}
