package com.example.data.repository

import com.example.data.mapper.toLoginRequestDTO
import com.example.data.mapper.toLoginResponse
import com.example.data.mapper.toRegisterRequestDTO
import com.example.data.mapper.toRegisterResponse
import com.example.data.netwok.MovieApiService
import com.example.domain.entity.LoginRequest
import com.example.domain.entity.LoginResponse
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

            println(" Sending Register Request: $registerRequestDTO")

            val response = movieApiService.register(registerRequestDTO)

            println(" API Response: $response")

            Result.success(response.toRegisterResponse())
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            println(" API Error (${e.code()}): $errorBody")
            Result.failure(Exception(errorBody ?: "Unknown error"))
        } catch (e: Exception) {
            println(" Unexpected Error: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return try {

            val loginRequestDTO = loginRequest.toLoginRequestDTO()

            val response = movieApiService.login(loginRequestDTO)
            Result.success(response.toLoginResponse())
        } catch (e: retrofit2.HttpException) {

            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Unknown Error"))


        }
    }
}