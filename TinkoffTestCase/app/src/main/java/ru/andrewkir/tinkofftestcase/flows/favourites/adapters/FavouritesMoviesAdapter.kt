package ru.andrewkir.tinkofftestcase.flows.favourites.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.andrewkir.domain.models.MovieModel
import ru.andrewkir.tinkofftestcase.R
import ru.andrewkir.tinkofftestcase.flows.main.adapters.MoviesAdapter


class FavouritesMoviesAdapter(
    private val listener: (MovieModel?) -> Unit,
    private val longClickListener: (MovieModel?) -> Unit
) :
    RecyclerView.Adapter<FavouritesMoviesAdapter.ViewHolder>() {

    var dataSet: List<MovieModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var favouritesList: List<MovieModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val description: TextView = view.findViewById(R.id.description)
        val poster: ImageView = view.findViewById(R.id.imageView)
        val star: ImageView = view.findViewById(R.id.star)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_row, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.run {
            name.text = dataSet[position].name
            description.text = dataSet[position].genres?.get(0) ?: ""
            if (favouritesList.find { it.id == (dataSet[position].id ?: 0) } != null) {
                star.visibility = View.VISIBLE
            } else {
                star.visibility = View.GONE
            }
            Glide
                .with(poster.context)
                .load(dataSet[position].posterUrl)
                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(25)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(poster)

            (name.parent.parent as View).setOnLongClickListener {
                longClickListener.invoke(dataSet[position])
                if (star.isVisible) star.visibility = View.GONE
                else star.visibility = View.VISIBLE
                true
            }
            (name.parent.parent as View).setOnClickListener {
                listener.invoke(dataSet[position])
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size
}