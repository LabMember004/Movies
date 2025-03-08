package com.example.domain.useCase

import com.example.domain.entity.Movies
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Result<List<Movies>> {
        return movieRepository.getFavorites()
    }
}