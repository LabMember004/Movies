package com.example.presentation.ProfileScreen.ProfileSetting.UpdateEmail

import androidx.compose.ui.util.trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.UpdateEmailRequest
import com.example.domain.entity.UpdateEmailResponse
import com.example.domain.repository.TokenRepositoryDomain
import com.example.domain.useCase.UpdateEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateEmailViewModel @Inject constructor(
    private val updateEmailUseCase: UpdateEmailUseCase,
    private val tokenRepository: TokenRepositoryDomain
) : ViewModel() {


    fun updateEmail(newEmail: String) {
        val updateEmailRequest = UpdateEmailRequest(newEmail)

        viewModelScope.launch {

            tokenRepository.getToken().collect { token ->

                if (token != null) {
                    updateEmailUseCase(updateEmailRequest, token)
                }


            }
        }
    }
}


