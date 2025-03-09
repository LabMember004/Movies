package com.example.ourmovies.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.TokenRepositoryDomain
import com.example.domain.entity.FavoriteRequest
import com.example.domain.entity.Movies
import com.example.domain.useCase.AddToFavoriteUseCase
import com.example.domain.useCase.DeleteFavoriteUseCase
import com.example.domain.useCase.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val tokenRepository: TokenRepositoryDomain
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var favoriteMovies = mutableStateOf<List<Movies>>(emptyList())

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val result = getFavoriteUseCase()
                result.onSuccess { movies ->
                    favoriteMovies.value = movies
                }.onFailure {
                    errorMessage.value = it.localizedMessage ?: "Unknown error"
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addFavoriteMovie(movie: Movies) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                tokenRepository.getToken().collect { token ->
                    if (!token.isNullOrEmpty()) {
                        val request = FavoriteRequest(movie.id)
                        val result = addToFavoriteUseCase(request)
                        result.onSuccess {
                            fetchFavoriteMovies()
                        }.onFailure {
                            errorMessage.value = it.localizedMessage ?: "Unknown error"
                        }
                    } else {
                        errorMessage.value = "Token is not available"
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }
    fun deleteFavoriteMovie(favoriteId:String) {
        viewModelScope.launch {
            try {
                val result = deleteFavoriteUseCase(favoriteId)
                result.onSuccess {
                    fetchFavoriteMovies()
                }.onFailure {
                    errorMessage.value = it.localizedMessage?: "Failed To Delete Movie"
                }



            } catch (e:Exception) {
                Log.e("FavoriteViewModel" , "Error deleting favorite: ${e.message}")
                errorMessage.value = e.localizedMessage ?: "Unknown error"

            }
        }
    }
}
