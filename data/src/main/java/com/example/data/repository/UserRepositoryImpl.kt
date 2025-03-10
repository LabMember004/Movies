package com.example.data.repository

import android.util.Log
import com.example.data.mapper.toLoginRequestDTO
import com.example.data.mapper.toLoginResponse
import com.example.data.mapper.toRegisterRequestDTO
import com.example.data.mapper.toRegisterResponse
import com.example.data.mapper.toUpdateEmailRequestDTO
import com.example.data.mapper.toUpdateEmailResponse
import com.example.data.mapper.toUpdatePasswordRequestDTO
import com.example.data.mapper.toUpdatePasswordResponse
import com.example.data.model.UpdateEmailRequestDTO
import com.example.data.netwok.MovieApiService
import com.example.domain.entity.LoginRequest
import com.example.domain.entity.LoginResponse
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.entity.UpdateEmailRequest
import com.example.domain.entity.UpdateEmailResponse
import com.example.domain.entity.UpdatePasswordRequest
import com.example.domain.entity.UpdatePasswordResponse
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

            val response = movieApiService.updateEmail(updateEmailRequestDTO)

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    Result.success(responseBody.toUpdateEmailResponse())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Unknown HTTP error"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePassword(updatePasswordRequest: UpdatePasswordRequest): Result<UpdatePasswordResponse> {
        return try {
            val updatePasswordRequestDTO = updatePasswordRequest.toUpdatePasswordRequestDTO()

            val response = movieApiService.updatePassword(updatePasswordRequestDTO)

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    Result.success(responseBody.toUpdatePasswordResponse())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Unknown HTTP error"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun deleteProfile(): Result<Unit> {
        return try {
            val deleteProfile = movieApiService.deleteProfile()

            if (deleteProfile.isSuccessful) {
                Log.d("UserRepositoryImpl", "User deleted")
                Result.success(Unit)
            } else {
                Log.d("UserRepositoryImpl", "Can't be deleted")
                Result.failure(Exception("Failed to delete profile"))
            }
        } catch (e: Exception) {
            Log.d("UserRepoImpl", "Error: ${e.message}")
            Result.failure(e)
        }
    }



}