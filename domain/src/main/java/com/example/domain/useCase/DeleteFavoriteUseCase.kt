package com.example.domain.useCase

import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(token: String, favoriteId: String): Result<Unit> {
        return movieRepository.deleteFavorite(token,favoriteId)
    }
}