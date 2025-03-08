package com.example.ourmovies.presentation.viewModels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.TokenRepositoryDomain
import com.example.domain.entity.FavoriteRequest
import com.example.domain.entity.Movies
import com.example.domain.useCase.AddToFavoriteUseCase
import com.example.domain.useCase.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val tokenRepository: TokenRepositoryDomain
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var favoriteMovies = mutableStateOf<List<Movies>>(emptyList())

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            isLoading.value = true
            Log.d("FavoriteViewModel", "Fetching favorite movies...")
            try {
                val result = getFavoriteUseCase()
                result.onSuccess { movies ->
                    favoriteMovies.value = movies
                    Log.d("FavoriteViewModel", "Fetched ${movies.size} favorite movies")
                }.onFailure {
                    errorMessage.value = it.localizedMessage ?: "Unknown error"
                    Log.e("FavoriteViewModel", "Error fetching favorites: ${it.localizedMessage}")
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
                Log.e("FavoriteViewModel", "Exception: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addFavoriteMovie(movie: Movies) {
        viewModelScope.launch {
            isLoading.value = true
            Log.d("FavoriteViewModel", "Adding movie ${movie.title} to favorites...")
            try {
                tokenRepository.getToken().collect { token ->
                    if (!token.isNullOrEmpty()) {
                        val request = FavoriteRequest(movie.id)
                        val result = addToFavoriteUseCase(request)
                        result.onSuccess {
                            Log.d("FavoriteViewModel", "Successfully added ${movie.title} to favorites.")
                            fetchFavoriteMovies() // Refresh list
                        }.onFailure {
                            errorMessage.value = it.localizedMessage ?: "Unknown error"
                            Log.e("FavoriteViewModel", "Failed to add favorite: ${it.localizedMessage}")
                        }
                    } else {
                        errorMessage.value = "Token is not available"
                        Log.e("FavoriteViewModel", "Token not available.")
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
                Log.e("FavoriteViewModel", "Exception: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }
}
