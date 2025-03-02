package com.example.data.mapper

import com.example.data.model.FavoriteRequestDTO
import com.example.domain.entity.FavoriteRequest

fun FavoriteRequestDTO.toFavoriteRequest(): FavoriteRequest {
    return FavoriteRequest(
        movieId = this.movieId
    )
}

fun FavoriteRequest.toFavoriteRequestDTO(): FavoriteRequestDTO {
    return FavoriteRequestDTO(
        movieId = this.movieId
    )
}