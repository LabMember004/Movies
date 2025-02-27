package com.example.domain.entity

data class HighlightedMovie(
    val id: String,
    val title: String,
    val tagline: String,
    val plot: String,
    val releaseYear: Int,
    val poster: String,
    val rating : Double,
    val runtime: Int,
    val genres: List<String>,
    val trailer: String
)
