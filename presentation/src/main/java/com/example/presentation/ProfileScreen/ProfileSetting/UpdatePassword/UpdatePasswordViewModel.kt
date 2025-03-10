package com.example.presentation.ProfileScreen.ProfileSetting.UpdatePassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.UpdatePasswordRequest
import com.example.domain.repository.TokenRepositoryDomain
import com.example.domain.repository.UserRepository
import com.example.domain.useCase.UpdatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase,
): ViewModel() {

    fun updatePassword(currentPassword: String, newPassword:String, confirmNewPassword: String ) {
        val updatePasswordRequest = UpdatePasswordRequest(currentPassword , newPassword, confirmNewPassword)

        viewModelScope.launch {

                updatePasswordUseCase(updatePasswordRequest)

        }
    }



}