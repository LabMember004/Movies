package com.example.data.model

data class SectionResponseDTO(
    val highlighted: HighlightedMovieDTO,
    val sections: List<SectionDTO>
)
