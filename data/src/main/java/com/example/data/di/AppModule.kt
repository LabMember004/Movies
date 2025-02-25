package com.example.data.di

import com.example.data.netwok.MovieApiService
import com.example.data.repository.MovieRepositoryImpl
import com.example.domain.repository.MovieRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { provideMovieApiService(get()) } // Retrofit instance injected by Koin
    single { MovieRepositoryImpl(get()) as MovieRepository }
}

// Function to provide MovieApiService
fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
    return retrofit.create(MovieApiService::class.java)
}
