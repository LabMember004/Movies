package com.example.presentation.ProfileScreen.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.LoginResponse
import com.example.domain.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<Result<LoginResponse>?> (null)
    val registerState: StateFlow<Result<LoginResponse>?> = _loginState


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = loginUseCase(email, password)
                _loginState.value = result

            } catch(e: Exception) {
                _loginState.value = Result.failure(e)
                println("Login Error : ${e.message}")

            }
        }

    }
}