package com.example.domain.repository

import com.example.domain.entity.MoviesResponse
import com.example.domain.entity.SectionResponse


interface MovieRepository {
    suspend fun getMovies(page: Int, genres: String? , releaseYear:Int? , query: String?): MoviesResponse

    suspend fun getSections(): SectionResponse
}