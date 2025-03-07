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
    private val _loginState = MutableStateFlow<Result<LoginResponse>?> (null)
    val registerState: StateFlow<Result<LoginResponse>?> = _loginState

    private val _tokenState = MutableStateFlow<String?>(null)
    val tokenState: StateFlow<String?> = _tokenState


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = loginUseCase(email, password)
                _loginState.value = result

                result.onSuccess { loginResponse ->
                    loginResponse.token?.let {
                        Log.d("LoginViewModel", "Token received: $it")

                        tokenUseCase.saveToken(it)
                    }
                }

            } catch(e: Exception) {

                Log.e("LoginViewModel", "Login error: ${e.message}")

                _loginState.value = Result.failure(e)


            }
        }

    }

    fun getToken() {
        viewModelScope.launch {
            tokenUseCase.getToken().collectLatest {
                Log.d("LoginViewModel", "Token fetched in ViewModel: $it")

                _tokenState.value = it
            }
        }
    }
}