package com.example.presentation.AllMoviesPage

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Movies
import com.example.domain.useCase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowsePageViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    private val _data = MutableStateFlow<List<Movies>>(emptyList())
    val data: StateFlow<List<Movies>> = _data.asStateFlow()



    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentPage = 1
    var isLoading by mutableStateOf(false)
    var isEndReached by mutableStateOf(false)

    fun fetchMovies(page: Int, genres: String? = null, releaseYear: Int? = null, query: String? = null) {
        if (isLoading || isEndReached) return
        isLoading = true

        Log.d("MoviesViewModel", "Fetching movies for page: $page")

        viewModelScope.launch {
            try {
                val response = getMoviesUseCase(page, genres, releaseYear, query)
                Log.d("MoviesViewModel", "Response received: ${response.data.size} movies")

                if (response.data.isNotEmpty()) {
                    _data.value += response.data
                    currentPage = page
                    isEndReached = response.totalPages <= page
                } else {
                    Log.d("MoviesViewModel", "No movies returned for page $page")
                }
            } catch (e: Exception) {
                Log.e("MoviesViewModel", "Error fetching movies: ${e.message}")
                _error.value = "Failed to load movies."
            } finally {
                isLoading = false
            }
        }
    }


    fun loadNextPage() {
        if(!isEndReached) {
            fetchMovies(currentPage +1)
        }
    }
}
