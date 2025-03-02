package com.example.data.model

data class RegisterRequestDTO(

    val email: String,
    val name: String,

    val password: String,
    val confirmPassword:String
)
