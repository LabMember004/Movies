package com.example.domain.useCase

import com.example.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val result = userRepository.deleteProfile()
                if (result.isSuccess) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Failed to delete the profile"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}