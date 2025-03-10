package com.example.domain.repository

import com.example.domain.entity.LoginRequest
import com.example.domain.entity.LoginResponse
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.entity.UpdateEmailRequest
import com.example.domain.entity.UpdateEmailResponse
import com.example.domain.entity.UpdatePasswordRequest
import com.example.domain.entity.UpdatePasswordResponse

interface UserRepository {

    suspend fun register(registerRequest: RegisterRequest): Result <RegisterResponse>

    suspend fun login(loginRequest: LoginRequest): Result <LoginResponse>

    suspend fun updateEmail(updateEmailRequest: UpdateEmailRequest ): Result<UpdateEmailResponse>

    suspend fun updatePassword(updatePasswordRequest: UpdatePasswordRequest): Result<UpdatePasswordResponse>

    suspend fun deleteProfile(): Result<Unit>

}