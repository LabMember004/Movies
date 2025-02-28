package com.example.data.model

data class RegisterRequestDTO(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword:String
)
