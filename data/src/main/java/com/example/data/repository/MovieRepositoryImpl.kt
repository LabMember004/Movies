package com.example.data.repository

import android.util.Log
import com.example.data.mapper.toFavoriteRequestDTO
import com.example.data.mapper.toMovieResponse
import com.example.data.mapper.toMovies
import com.example.data.mapper.toRegisterRequestDTO
import com.example.data.mapper.toRegisterResponse
import com.example.data.mapper.toSectionResponse
import com.example.data.model.FavoriteRequestDTO
import com.example.data.model.RegisterRequestDTO
import com.example.data.netwok.MovieApiService
import com.example.domain.entity.FavoriteRequest
import com.example.domain.entity.Movies
import com.example.domain.entity.MoviesResponse
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.entity.SectionResponse
import com.example.domain.repository.MovieRepository
import com.example.domain.useCase.TokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val tokenUseCase: TokenUseCase
): MovieRepository {
    override suspend fun getMovies(
        page: Int,
        genres: String?,
        releaseYear: Int?,
        query: String?
    ): MoviesResponse {
        return movieApiService.getMovies(page, genres , releaseYear , query).toMovieResponse()
    }

    override suspend fun getSections(): SectionResponse {
        return movieApiService.getSections().toSectionResponse()


    }

    override suspend fun addToFavorites(request: FavoriteRequest): Result<Unit> {
        return try {
            val response = movieApiService.addToFavorites(request.toFavoriteRequestDTO())

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to add to favorites: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getFavorites(): Result<List<Movies>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.getFavorites()
                if (response.isSuccessful) {
                    Result.success(response.body()?.map { it.toMovies() } ?: emptyList())
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun deleteFavorite(favoriteId: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApiService.deleteFavorite(favoriteId)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }






}
