package com.example.data.model

data class AllMoviesResponse(
    val totalDocs: Int,
    val totalPages: Int,
    val currentPage: Int,
    val data : List<AllMovies>
)
