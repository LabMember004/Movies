package com.example.movies.di

import com.example.domain.useCase.GetMoviesUseCase
import com.example.presentation.AllMoviesPage.AllMoviesScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideAllMoviesViewModel(getMoviesUseCase: GetMoviesUseCase): AllMoviesScreenViewModel {
        return AllMoviesScreenViewModel(getMoviesUseCase)
    }
}