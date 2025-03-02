package com.example.domain.useCase

import com.example.domain.entity.FavoriteRequest
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(token: String, request: FavoriteRequest) = movieRepository.addToFavorites(token,request)
}