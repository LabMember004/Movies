package com.example.presentation.ProfileScreen.ProfileSetting.UpdatePassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3. OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UpdatePasswordScreen(viewModel: UpdatePasswordViewModel = hiltViewModel(), onPasswordChanged: () -> Unit ) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

    var isFormValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            label = { Text("Current Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmNewPassword,
            onValueChange = { confirmNewPassword = it },
            label = { Text("Confirm New Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (currentPassword.isBlank() || newPassword.isBlank() || confirmNewPassword.isBlank()) {
                    isFormValid = false
                    errorMessage = "All fields must be completed."
                    return@Button
                }

                if (newPassword != confirmNewPassword) {
                    isFormValid = false
                    errorMessage = "New password and confirm new password must match."
                    return@Button
                }

                isFormValid = true
                viewModel.updatePassword(currentPassword, newPassword, confirmNewPassword)

                message = "Password Successfully Changed"
                isSuccess = true
                onPasswordChanged()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Password")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isFormValid) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        if (message.isNotEmpty()) {
            Text(text = message, color = if (isSuccess) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
        }
    }
}



