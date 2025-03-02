package com.example.domain.useCase

import com.example.domain.entity.LoginRequest
import com.example.domain.entity.LoginResponse
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Result <LoginResponse> {
        return userRepository.login(LoginRequest(email , password))
    }

}