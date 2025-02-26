package com.example.domain.useCase

import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MovieRepository
) {
    suspend operator fun invoke(
        page: Int,
        genres: String? = null,
        releaseYear: Int? = null,
        query: String? = null
    ) = moviesRepository.getMovies(page, genres, releaseYear, query)
}
