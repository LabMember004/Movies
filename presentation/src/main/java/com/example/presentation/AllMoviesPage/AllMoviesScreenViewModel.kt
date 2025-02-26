package com.example.presentation.AllMoviesPage

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
class AllMoviesScreenViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    private val _data = MutableStateFlow<List<Movies>>(emptyList())
    val data: StateFlow<List<Movies>> = _data.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchMovies(page: Int, genres: String? = null, releaseYear: Int? = null, query: String? = null) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = getMoviesUseCase(page, genres, releaseYear, query)
                _data.value = response.data
            } catch (e: Exception) {
                _error.value = "Failed to fetch movies: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
