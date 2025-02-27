package com.example.data.mapper

import com.example.data.model.AllMovies
import com.example.domain.entity.Movies

 fun AllMovies.toMovies(): Movies {
    return Movies(
        id = this._id,
        title = this.title,
        releaseYear = this.releaseYear,
        poster = this.poster,
        rating = this.rating,
        runtime = this.runtime,
        genres = this.genres,
        trailer = this.trailer
    )
}