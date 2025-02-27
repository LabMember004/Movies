package com.example.presentation.HomePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.SectionResponse
import com.example.domain.entity.Sections
import com.example.domain.useCase.GetMoviesSectionsUseCase
import com.example.domain.useCase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val getMoviesSectionsUseCase: GetMoviesSectionsUseCase
) : ViewModel() {

    private val _sections = MutableStateFlow<List<Sections>>(emptyList())
    val sections: StateFlow<List<Sections>> = _sections.asStateFlow()


    fun getSections() {
        viewModelScope.launch {
            try {
                val response : SectionResponse = getMoviesSectionsUseCase()
                _sections.value = response.sections
            } catch (e: Exception) {

            }
        }
    }


}