package com.example.presentation.ProfileScreen.Login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.LoginResponse
import com.example.domain.useCase.LoginUseCase
import com.example.domain.useCase.TokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenUseCase: TokenUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginState: StateFlow<Result<LoginResponse>?> = _loginState

    private val _tokenState = MutableStateFlow<String?>(null)
    val tokenState: StateFlow<String?> = _tokenState

    private fun validateInput(email: String, password: String): Result<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Email and Password cannot be empty"))
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(Exception("Invalid email format"))
        }
        return Result.success(Unit)
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            validateInput(email, password).onFailure {
                _loginState.value = Result.failure(it)
                return@launch
            }

            try {
                val result = loginUseCase(email, password)
                _loginState.value = result

                result.onSuccess { loginResponse ->
                    loginResponse.token?.let {
                        Log.d("LoginViewModel", "Token received: $it")
                        tokenUseCase.saveToken(it)
                    }
                }

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login error: ${e.message}")
                _loginState.value = Result.failure(e)
            }
        }
    }
}
