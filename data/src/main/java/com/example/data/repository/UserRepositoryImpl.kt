package com.example.data.repository

import com.example.data.mapper.toLoginRequestDTO
import com.example.data.mapper.toLoginResponse
import com.example.data.mapper.toRegisterRequestDTO
import com.example.data.mapper.toRegisterResponse
import com.example.data.mapper.toUpdateEmailRequestDTO
import com.example.data.mapper.toUpdateEmailResponse
import com.example.data.model.UpdateEmailRequestDTO
import com.example.data.netwok.MovieApiService
import com.example.domain.entity.LoginRequest
import com.example.domain.entity.LoginResponse
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.entity.UpdateEmailRequest
import com.example.domain.entity.UpdateEmailResponse
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
): UserRepository {

    override suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        return try {
            val registerRequestDTO = registerRequest.toRegisterRequestDTO()


            val response = movieApiService.register(registerRequestDTO)


            Result.success(response.toRegisterResponse())
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Unknown error"))
        } catch (e: Exception) {
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

    override suspend fun updateEmail(updateEmailRequest: UpdateEmailRequest): Result<UpdateEmailResponse> {
        return try {
            val updateEmailRequestDTO = updateEmailRequest.toUpdateEmailRequestDTO()

            // Call the API to update email
            val response = movieApiService.updateEmail(updateEmailRequestDTO)

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    Result.success(responseBody.toUpdateEmailResponse())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: retrofit2.HttpException) {
            // Handle HTTP exception (e.g., 404, 500 errors)
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Unknown HTTP error"))
        } catch (e: Exception) {
            // Handle other exceptions (e.g., network failure, parsing errors)
            Result.failure(e)
        }
    }


}