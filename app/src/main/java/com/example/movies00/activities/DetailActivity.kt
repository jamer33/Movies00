package com.example.movies00.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movies00.R
import com.example.movies00.data.Movie
import com.example.movies00.data.MovieService
import com.example.movies00.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE_ID = "MOVIE_ID"
    }
    lateinit var binding: ActivityDetailBinding
    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val movieId = intent.getStringExtra(EXTRA_MOVIE_ID)
        getMovie(movieId!!)
    }

    fun getMovie(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = MovieService.getInstance()
                movie = service.getMovieById("326b2db8", id)
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    @SuppressLint("SetTextI18n")
    fun loadData() {
        Picasso.get().load(movie.poster).into(binding.thumbnailImageView)

        supportActionBar?.title = movie.title
        binding.titleTextView.text = movie.title
        binding.yearTextView.text = movie.year
        binding.country.text = movie.country
        binding.duration.text = " " + (movie.runtime)
        binding.directorTextView.text = movie.director
        binding.plotTextView.text = movie.plot













    }






}