package com.example.domain.entity

data class UpdatePasswordRequest(
    val currentPassword: String,
    val newPassword: String,
    val confirmNewPassword: String
)
