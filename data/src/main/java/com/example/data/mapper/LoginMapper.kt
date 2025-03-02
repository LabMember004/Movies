package com.example.data.mapper

import com.example.data.model.LoginRequestDTO
import com.example.data.model.LoginResponseDTO
import com.example.domain.entity.LoginRequest
import com.example.domain.entity.LoginResponse


fun LoginRequestDTO.toLoginRequest(): LoginRequest {
    return LoginRequest(
        email = this.email,
        password = this.password,

        )
}

fun LoginResponseDTO.toLoginResponse(): LoginResponse {
    return LoginResponse(
        token = this.token,
        error = this.error,
    )
}



fun LoginRequest.toLoginRequestDTO(): LoginRequestDTO{
    return LoginRequestDTO(
        email = this.email,
        password= this.password,

    )
}