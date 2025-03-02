package com.example.domain.repository

import com.example.domain.entity.LoginRequest
import com.example.domain.entity.LoginResponse
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse

interface UserRepository {

    suspend fun register(registerRequest: RegisterRequest): Result <RegisterResponse>

    suspend fun login(loginRequest: LoginRequest): Result <LoginResponse>

}