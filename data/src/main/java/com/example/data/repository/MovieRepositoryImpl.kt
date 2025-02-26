package com.example.data.repository

import com.example.data.mapper.toMovieResponse
import com.example.data.netwok.MovieApiService
import com.example.domain.entity.MoviesResponse
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
): MovieRepository {
    override suspend fun getMovies(
        page: Int,
        genres: String?,
        releaseYear: Int?,
        query: String?
    ): MoviesResponse {
        return movieApiService.getMovies(page, genres , releaseYear , query).toMovieResponse()
    }
}
