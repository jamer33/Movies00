package com.example.movies00.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies00.R
import com.example.movies00.adapters.MovieAdapter
import com.example.movies00.data.Movie
import com.example.movies00.data.MovieService
import com.example.movies00.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MovieAdapter

    var filteredMovieList: List<Movie> = emptyList()
    var originalMovieList: List<Movie> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = MovieAdapter(filteredMovieList) { position ->
            val movie = filteredMovieList[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.imdbID)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    searchView.clearFocus()

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val service = MovieService.getInstance()
                            val movieSearchResponse = service.searchMovies("326b2db8", query).movies
                            originalMovieList = movieSearchResponse
                            filteredMovieList = originalMovieList
                            CoroutineScope(Dispatchers.Main).launch {
                                adapter.updateItems(filteredMovieList)
                                Log.i("API_SUCCESS", "Películas encontradas: ${filteredMovieList.size}")
                            }

                        } catch (e: Exception) {
                            Log.e("API_ERROR", "Error al buscar películas", e)
                        }
                    }
                    return true // Indicamos que hemos manejado la acción.
                }
                return false // Dejamos que el sistema maneje el evento si el texto está vacío.
            }

            override fun onQueryTextChange(newText: String): Boolean {
               filteredMovieList = originalMovieList.filter { it.title.contains(newText, true) }
               adapter.updateItems(filteredMovieList)
                return true
            }
        })

        return true
    }


}