package com.example.presentation.AllMoviesPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Movies
import com.example.domain.useCase.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllMoviesScreenViewModel(private val getMoviesUseCase: GetMoviesUseCase): ViewModel() {
    private val _data = MutableStateFlow<List<Movies>>(emptyList())
    val data: StateFlow<List<Movies>> = _data.asStateFlow()


    fun fetchMovies() {
        viewModelScope.launch {

        }
    }
}