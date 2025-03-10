package com.example.movies.di

import android.content.Context
import com.example.data.netwok.MovieApiService
import com.example.data.repository.MovieRepositoryImpl
import com.example.data.repository.TokenRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.TokenRepositoryDomain
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.UserRepository
import com.example.domain.useCase.GetMoviesUseCase
import com.example.domain.useCase.RegisterUseCase
import com.example.domain.useCase.TokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(movieApiService: MovieApiService, ): MovieRepository {
        return MovieRepositoryImpl(movieApiService)
    }

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(repository: MovieRepository): GetMoviesUseCase {
        return GetMoviesUseCase(repository)
    }

    @Provides
    fun provideUserRepository(movieApiService: MovieApiService): UserRepository {
        return UserRepositoryImpl(movieApiService)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(userRepository: UserRepository): RegisterUseCase {
        return RegisterUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideTokenRepository(@ApplicationContext context:Context): TokenRepositoryDomain {
        return TokenRepositoryImpl(context)
    }

}
