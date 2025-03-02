package com.example.presentation.ProfileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.RegisterUseCase
import com.example.domain.entity.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<Result<RegisterResponse>?>(null)
    val registerState: StateFlow<Result<RegisterResponse>?> = _registerState

    fun registerUser(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                val result = registerUseCase(name, email, password, confirmPassword)
                _registerState.value = result
            } catch (e: Exception) {
                _registerState.value = Result.failure(e)
                println("Registration Error: ${e.message}")
            }
        }
    }

}
