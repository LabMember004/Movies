package com.example.data.repository

import com.example.data.mapper.toMovieResponse
import com.example.data.mapper.toRegisterRequestDTO
import com.example.data.mapper.toRegisterResponse
import com.example.data.mapper.toSectionResponse
import com.example.data.model.RegisterRequestDTO
import com.example.data.netwok.MovieApiService
import com.example.domain.entity.MoviesResponse
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.entity.SectionResponse
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
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

    override suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        return try {
            val registerRequestDTO = registerRequest.toRegisterRequestDTO()

            // 🔍 Debugging: Log the outgoing request
            println("📤 Sending Register Request: $registerRequestDTO")

            val response = movieApiService.register(registerRequestDTO)

            // ✅ Debugging: Log the API response
            println("✅ API Response: $response")

            Result.success(response.toRegisterResponse())
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            println("❌ API Error (${e.code()}): $errorBody") // Log error
            Result.failure(Exception(errorBody ?: "Unknown error"))
        } catch (e: Exception) {
            println("❌ Unexpected Error: ${e.message}") // Log general errors
            Result.failure(e)
        }
    }


}
