package com.example.movies00.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies00.R
import com.example.movies00.data.Movie
import com.example.movies00.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(
    var items: List<Movie>,
    val onClickListener: (Int) -> Unit
    ) : RecyclerView.Adapter<MovieViewHolder>() {

    // Cual es la vista para los elementos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    // Cuales son los datos para el elemento
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    // Cuantos elementos se quieren listar?
    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(movie: Movie) {
        Picasso.get().load(movie.poster).into(binding.thumbnailImageView)
        binding.nameTextView.text = movie.title
        binding.yearTextView.text = movie.year
    }
}