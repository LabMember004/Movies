package com.example.domain.repository

import android.telecom.Call
import com.example.domain.entity.FavoriteRequest
import com.example.domain.entity.Movies
import com.example.domain.entity.MoviesResponse
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.entity.SectionResponse


interface MovieRepository {
    suspend fun getMovies(page: Int, genres: String? , releaseYear:Int? , query: String?): MoviesResponse

    suspend fun getSections(): SectionResponse

    suspend fun addToFavorites(
        token: String,
        request: FavoriteRequest
    ): Result<Unit>

    suspend fun getFavorites(
        token: String
    ):Result <List<Movies>>

    suspend fun deleteFavorite(token: String, favoriteId: String): Result<Unit>

}