package com.example.domain.useCase

import android.net.http.HttpException
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<RegisterResponse> {


        return userRepository.register(RegisterRequest(name, email, password, confirmPassword))
    }



}
