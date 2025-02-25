package com.example.data.mapper

import com.example.data.model.AllMoviesResponse
import com.example.domain.entity.MoviesResponse

fun AllMoviesResponse.toMovieResponse(): MoviesResponse {
    return MoviesResponse(
        totalDocs = this.totalDocs,
        totalPages = this.totalPages,
        currentPage = this.currentPage,
        data = this.data.map {it.toMovies()}
    )
}