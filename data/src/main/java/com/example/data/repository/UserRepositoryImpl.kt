package com.example.data.repository

import com.example.data.mapper.toRegisterRequestDTO
import com.example.data.mapper.toRegisterResponse
import com.example.data.netwok.MovieApiService
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
): UserRepository {

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