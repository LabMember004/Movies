package com.example.domain

import com.example.domain.entity.UpdatePasswordRequest
import com.example.domain.entity.UpdatePasswordResponse
import com.example.domain.repository.UserRepository
import com.example.domain.useCase.UpdatePasswordUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdatePasswordUseCaseTest {

    private lateinit var updatePasswordUseCase: UpdatePasswordUseCase
    private val userRepository: UserRepository = mockk()

    @Before
    fun setUp() {
        updatePasswordUseCase = UpdatePasswordUseCase(userRepository)
    }

    @Test
    fun `invoke should return success when updatePassword is successful`() = runBlocking {
        val request = UpdatePasswordRequest("oldPassword", "newPassword", "newPassword")
        val response = UpdatePasswordResponse(success = true, message = "Password updated successfully")
        coEvery { userRepository.updatePassword(request) } returns Result.success(response)

        val result = updatePasswordUseCase(request)

        assertTrue(result.isSuccess)
        assertEquals(response, result.getOrNull())
        coVerify { userRepository.updatePassword(request) }
    }

    @Test
    fun `invoke should return failure when updatePassword fails`() = runBlocking {
        val request = UpdatePasswordRequest("oldPassword", "newPassword", "newPassword")
        val exception = Exception("Update failed")
        coEvery { userRepository.updatePassword(request) } returns Result.failure(exception)

        val result = updatePasswordUseCase(request)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { userRepository.updatePassword(request) }
    }
}
