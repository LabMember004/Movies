package com.example.domain.useCase

import com.example.domain.entity.UpdateEmailRequest
import com.example.domain.entity.UpdateEmailResponse
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UpdateEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(updateEmailRequest: UpdateEmailRequest, token: String): Result<UpdateEmailResponse> {
        return try {
           userRepository.updateEmail(updateEmailRequest)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
