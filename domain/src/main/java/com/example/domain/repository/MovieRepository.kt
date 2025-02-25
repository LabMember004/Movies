package com.example.domain.repository

import com.example.domain.entity.MoviesResponse


interface MovieRepository {
    suspend fun getMovies(page: Int, genres: String? , releaseYear:Int? , query: String?): MoviesResponse
}