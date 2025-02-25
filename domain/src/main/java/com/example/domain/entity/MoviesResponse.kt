package com.example.domain.entity

data class MoviesResponse(
    val totalDocs: Int,
    val totalPages: Int,
    val currentPage: Int,
    val data: List<Movies>
)
