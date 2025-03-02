package com.example.domain

import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.UserRepository
import com.example.domain.useCase.RegisterUseCase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RegisterUseCaseTest {

    private val userRepository: UserRepository = mockk()
    private lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setUp() {
        registerUseCase = RegisterUseCase(userRepository)
    }

    @Test
    fun `invoke should call repository and return success`() = runBlocking {

        val request = RegisterRequest(
            name = "Heshar",
            email = "heshar@gmail.com",
            password = "12345678",
            confirmPassword = "12345678"
        )
        val expectedResponse = RegisterResponse(success = true, message = "Registration successful")

        coEvery { userRepository.register(any()) } returns Result.success(expectedResponse)


        val result = registerUseCase(request.name, request.email, request.password, request.confirmPassword)


        coVerify(exactly = 1) { userRepository.register(any()) }
        assertEquals(Result.success(expectedResponse), result)
    }

    @Test
    fun `invoke should return failure when repository fails`() = runBlocking {

        val request = RegisterRequest(
            name = "Heshar",
            email = "heshar@gmail.com",
            password = "12345678",
            confirmPassword = "12345678"
        )
        val exception = Exception("Registration failed")

        coEvery { userRepository.register(any()) } returns Result.failure(exception)

        val result = registerUseCase(request.name, request.email, request.password, request.confirmPassword)

        coVerify(exactly = 1) { userRepository.register(any()) }
        assertEquals(Result.failure<RegisterResponse>(exception), result)
    }
}
