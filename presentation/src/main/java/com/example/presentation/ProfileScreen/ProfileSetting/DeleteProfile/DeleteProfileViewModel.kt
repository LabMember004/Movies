package com.example.presentation.ProfileScreen.ProfileSetting.DeleteProfile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.DeleteProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteProfileViewModel @Inject constructor(
    private val deleteProfileUseCase: DeleteProfileUseCase
): ViewModel() {

    private val _deleteProfileResult = mutableStateOf<Result<Unit>?>(null)
    val deleteProfileResult: State<Result<Unit>?> = _deleteProfileResult

    fun deleteProfile() {
        viewModelScope.launch {
            val result = deleteProfileUseCase.invoke()
            _deleteProfileResult.value = result


        }
    }
}