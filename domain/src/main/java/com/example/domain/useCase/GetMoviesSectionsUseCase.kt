package com.example.domain.useCase

import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesSectionsUseCase @Inject constructor(
    private val moviesRepository: MovieRepository
) {
    suspend operator fun invoke() = moviesRepository.getSections()
}