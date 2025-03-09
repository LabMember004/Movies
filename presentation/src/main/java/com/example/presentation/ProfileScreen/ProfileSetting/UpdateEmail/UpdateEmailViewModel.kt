package com.example.presentation.ProfileScreen.ProfileSetting.UpdateEmail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.UpdateEmailRequest
import com.example.domain.entity.UpdateEmailResponse
import com.example.domain.useCase.UpdateEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateEmailViewModel @Inject constructor(
    private val updateEmailUseCase: UpdateEmailUseCase
) : ViewModel() {

    // LiveData or StateFlow to hold the update result
    private val _updateEmailResult = MutableStateFlow<Result<UpdateEmailResponse>>(Result.success(UpdateEmailResponse("")))
    val updateEmailResult: StateFlow<Result<UpdateEmailResponse>> = _updateEmailResult

    // Function to call the use case and update the email
    fun updateEmail(newEmail: String) {
        val updateEmailRequest = UpdateEmailRequest(newEmail)

        viewModelScope.launch {
            try {
                // Call the use case to update the email
                val result = updateEmailUseCase(updateEmailRequest)

                // Update the result in the StateFlow
                _updateEmailResult.value = result
            } catch (e: Exception) {
                // Handle exceptions if necessary
                _updateEmailResult.value = Result.failure(e)
            }
        }
    }
}
