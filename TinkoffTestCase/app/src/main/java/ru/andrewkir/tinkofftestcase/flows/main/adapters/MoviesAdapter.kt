package ru.andrewkir.tinkofftestcase.flows.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.andrewkir.domain.models.MovieModel
import ru.andrewkir.tinkofftestcase.R

class MoviesAdapter(
    private val listener: (MovieModel?) -> Unit,
    private val longClickListener: (MovieModel?) -> Unit
) :
    PagingDataAdapter<MovieModel, MoviesAdapter.ViewHolder>(MovieDiffCallBack()) {

    class MovieDiffCallBack : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val description: TextView = view.findViewById(R.id.description)
        val poster: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_row, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.run {
            name.text = getItem(position)?.name
            description.text = getItem(position)?.genres?.get(0) ?: ""
            Glide
                .with(poster.context)
                .load(getItem(position)?.posterUrl)
                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(25)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(poster)

            (name.parent.parent as View).setOnLongClickListener {
                longClickListener.invoke(getItem(position))
                true
            }
            (name.parent.parent as View).setOnClickListener {
                listener.invoke(getItem(position))
            }
        }
    }
}