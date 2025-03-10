package com.example.data.mapper

import com.example.data.model.UpdatePasswordRequestDTO
import com.example.data.model.UpdatePasswordResponseDTO
import com.example.domain.entity.UpdateEmailRequest
import com.example.domain.entity.UpdatePasswordRequest
import com.example.domain.entity.UpdatePasswordResponse

fun UpdatePasswordRequestDTO.toUpdatePasswordRequest(): UpdatePasswordRequest {
    return UpdatePasswordRequest(
        currentPassword = this.currentPassword,
        newPassword = this.newPassword,
        confirmNewPassword = this.confirmNewPassword
    )
}

fun UpdatePasswordResponseDTO.toUpdatePasswordResponse(): UpdatePasswordResponse {
    return UpdatePasswordResponse(
        success = this.success,
        message = this.message
    )
}

fun UpdatePasswordRequest.toUpdatePasswordRequestDTO(): UpdatePasswordRequestDTO {
    return UpdatePasswordRequestDTO(
        currentPassword = this.currentPassword,
        newPassword = this.newPassword,
        confirmNewPassword = this.confirmNewPassword
    )
}

fun UpdatePasswordResponse.toUpdatePasswordResponseDTO(): UpdatePasswordResponseDTO {
    return UpdatePasswordResponseDTO(
        success = this.success,
        message = this.message
    )
}