package com.example.ourmovies.presentation.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.FavoriteRequest
import com.example.domain.entity.Movies
import com.example.domain.useCase.AddToFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val addToFavoriteUseCase: AddToFavoriteUseCase
) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow<List<Movies>>(emptyList())
    val favoriteMovies: StateFlow<List<Movies>> get() = _favoriteMovies

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    fun addFavoriteMovie(movieId: String, token: String ) {

        viewModelScope.launch {
            try {
                val favoriteRequest = FavoriteRequest(movieId)
               val response = addToFavoriteUseCase(
                   token= "Bearer ${token}",
                   request = favoriteRequest
               )



            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }
}
