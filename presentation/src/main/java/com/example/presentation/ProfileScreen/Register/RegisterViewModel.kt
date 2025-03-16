package com.example.presentation.ProfileScreen.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.RegisterUseCase
import com.example.domain.entity.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel(), ProfileScreenInteraction {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun registerUser(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                val result = registerUseCase(name, email, password, confirmPassword)
                result.fold(
                    onSuccess = ::onRegisterSuccess,
                    onFailure = ::onRegisterFailure
                )
            } catch (e: Exception) {
                onRegisterFailure(e)
            }
        }
    }

    private fun onRegisterSuccess(response: RegisterResponse) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isSuccess = true,
                errorMessage = null
            )
        }
    }

    private fun onRegisterFailure(exception: Throwable) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isSuccess = false,
                errorMessage = exception.message
            )
        }
    }

    override fun updateUsername(newInput: String) {
        _uiState.update { it.copy(username = newInput) }
    }

    override fun updateEmail(newInput: String) {
        _uiState.update { it.copy(email = newInput) }
    }

    override fun updatePassword(newInput: String) {
        _uiState.update { it.copy(password = newInput) }
    }

    override fun updateConfirmPassword(newInput: String) {
        _uiState.update { it.copy(confirmPassword = newInput) }
    }

    override fun onClickRegister() {
        val currentState = _uiState.value
        if (currentState.username.isEmpty()) {
            println("Name is required")
            return
        }
        if (currentState.email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            println("Invalid email address")
            return
        }
        if (currentState.password.isEmpty()) {
            println("Password is required")
            return
        }
        if (currentState.confirmPassword != currentState.password) {
            println("Passwords do not match")
            return
        }

        registerUser(
            currentState.username,
            currentState.email,
            currentState.password,
            currentState.confirmPassword
        )
    }
}

