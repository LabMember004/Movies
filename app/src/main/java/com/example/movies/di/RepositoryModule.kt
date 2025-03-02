package com.example.movies.di

import com.example.data.netwok.MovieApiService
import com.example.data.repository.MovieRepositoryImpl
import com.example.domain.repository.MovieRepository
import com.example.domain.useCase.GetMoviesUseCase
import com.example.domain.useCase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(movieApiService: MovieApiService): MovieRepository {
        return MovieRepositoryImpl(movieApiService)
    }

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(repository: MovieRepository): GetMoviesUseCase {
        return GetMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(moviesRepository: MovieRepository): RegisterUseCase {
        return RegisterUseCase(moviesRepository)
    }
}
