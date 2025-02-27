package com.example.data.mapper

import com.example.data.model.HighlightedMovieDTO
import com.example.data.model.SectionDTO
import com.example.data.model.SectionResponseDTO
import com.example.domain.entity.HighlightedMovie
import com.example.domain.entity.SectionResponse
import com.example.domain.entity.Sections

fun SectionResponseDTO.toSectionResponse(): SectionResponse {
    return SectionResponse(
        highlighted = highlighted.toHighlightedMovie(),
        sections = sections.map { it.toSections() }
    )
}

fun SectionDTO.toSections(): Sections {
    return Sections(
        title = this.title,
        movies = this.movies.map {it.toMovies()}
    )


}

fun HighlightedMovieDTO.toHighlightedMovie(): HighlightedMovie {
    return HighlightedMovie(
        id = this._id,
        title = this.title,
        releaseYear = this.releaseYear,
        poster = this.poster,
        rating = this.rating,
        runtime = this.runtime,
        plot = this.plot,
        tagline = this.tagline,
        genres = this.genres,
        trailer = this.trailer
    )
}


