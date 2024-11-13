package com.example.movieapp.ui.theme.viewModel

import android.util.LruCache
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.MovieDetails
import com.example.movieapp.network.MovieApiManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesViewModel : ViewModel() {

    private val api: MovieApiManager

    private val moviesListCache = LruCache<String, List<MovieDetails>>(20)

    private val _moviesList = MutableStateFlow<List<MovieDetails>>(emptyList())
    val moviesList: StateFlow<List<MovieDetails>> = _moviesList

    private val _errorMessage = MutableStateFlow<String?>(null)

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MovieApiManager.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MovieApiManager::class.java)
    }


    fun searchMovies(movieName: String) {
        viewModelScope.launch {
            try {
                val cachedMovies = moviesListCache.get(movieName)
                if (cachedMovies != null) {
                    _moviesList.value = cachedMovies
                } else {
                    val movies = api.getMoviesList(movieName)
                    _moviesList.value = movies

                    moviesListCache.put(movieName, movies)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to search movies. Please try again."
            }
        }
    }
}
