package com.example.domain.useCase

import android.net.http.HttpException
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val moviesRepository: MovieRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<RegisterResponse> {
        return moviesRepository.register(RegisterRequest(name, email, password, confirmPassword))
    }


}
