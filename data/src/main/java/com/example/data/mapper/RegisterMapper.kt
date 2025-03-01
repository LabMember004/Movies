package com.example.data.mapper

import com.example.data.model.RegisterRequestDTO
import com.example.data.model.RegisterResponseDTO
import com.example.domain.entity.RegisterRequest
import com.example.domain.entity.RegisterResponse

fun RegisterRequestDTO.toRegisterRequest(): RegisterRequest {
    return RegisterRequest(
        name = this.name,
        email = this.email,
        password = this.password,
        confirmPassword = this.confirmPassword
    )
}


fun RegisterRequest.toRegisterRequestDTO(): RegisterRequestDTO {
    return RegisterRequestDTO(
        name = this.name,
        email = this.email,
        password = this.password,
        confirmPassword = this.confirmPassword
    )
}


fun RegisterResponseDTO.toRegisterResponse(): RegisterResponse {
    return RegisterResponse(
        message = this.message,
        success = this.success
    )
}