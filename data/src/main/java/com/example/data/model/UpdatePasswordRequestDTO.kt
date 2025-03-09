package com.example.data.model

data class UpdatePasswordRequestDTO(
    val currentPassword: String,
    val newPassword: String,
    val confirmNewPassword: String
)
