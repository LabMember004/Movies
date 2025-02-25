package com.example.domain.useCase

import com.example.domain.entity.MoviesResponse
import com.example.domain.repository.MovieRepository

class GetMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(
        page: Int,
        genres: String?,
        releaseYear: Int?,
        query: String?
    ): MoviesResponse {
        return movieRepository.getMovies(page, genres, releaseYear, query)
    }
}