package com.example.presentation.ProfileScreen.Register

data class ProfileUiState(
    val username: String ="",
    val email: String = "",
    val password: String = "" ,
    val confirmPassword:String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null

)