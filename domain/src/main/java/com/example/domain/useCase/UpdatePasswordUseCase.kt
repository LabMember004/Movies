package com.example.domain.useCase

import com.example.domain.entity.UpdatePasswordRequest
import com.example.domain.entity.UpdatePasswordResponse
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(updatePasswordRequest: UpdatePasswordRequest): Result<UpdatePasswordResponse> {
        return try {
            userRepository.updatePassword(updatePasswordRequest)
        }catch(e: Exception) {
            Result.failure(e)
        }
    }
}