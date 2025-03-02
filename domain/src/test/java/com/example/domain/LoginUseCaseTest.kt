package com.example.domain

import com.example.domain.entity.LoginRequest
import com.example.domain.entity.LoginResponse
import com.example.domain.repository.UserRepository
import com.example.domain.useCase.LoginUseCase
import com.example.domain.useCase.RegisterUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private val userRepository: UserRepository = mockk()
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(userRepository)
    }

    @Test
    fun `invoke should call repository and return success`() = runBlocking {
        val request = LoginRequest(
            email = "heshar@gmail.com",
            password = "12345678"
        )
        val expectedResponse = LoginResponse(token = "1234" , error = "Successful")

        coEvery { userRepository.login(any()) } returns Result.success(expectedResponse)

        val result = loginUseCase(request.email , request.password)

        coVerify(exactly = 1) {userRepository.login(any())}
        assertEquals(Result.success(expectedResponse) , result)
    }

    @Test
    fun `invoke should return failure when repository fails` () = runBlocking {
        val request = LoginRequest(
            email = "heshar@gmail.com",
            password = "12345678"
        )
        val exception = Exception("Login FAILED")

        coEvery { userRepository.login(any()) } returns Result.failure(exception)

        val result = loginUseCase(request.email, request.password)

        coVerify(exactly = 1) {userRepository.login(any())  }

        assertEquals(Result.failure<LoginResponse>(exception) , result)
    }
}