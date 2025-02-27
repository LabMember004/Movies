package com.example.data.model

import com.example.domain.entity.Movies

data class SectionDTO(
    val title: String,
    val movies: List<AllMovies>
)
