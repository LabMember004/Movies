package com.example.presentation.ProfileScreen.ProfileSetting.UpdateEmail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.util.Patterns

@Composable
fun UpdateEmailScreen(viewModel: UpdateEmailViewModel = hiltViewModel(), navController: NavController, onPasswordChanged: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("New Email") },
            modifier = Modifier.fillMaxWidth()
        )

        if (message.isNotEmpty()) {
            val messageColor = if (isSuccess) Color.Green else Color.Red
            Text(text = message, color = messageColor)
        }

        Button(
            onClick = {
                if (email.isBlank()) {
                    isSuccess = false
                    message = "Email is required"
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    isSuccess = false
                    message = "Please enter a valid email address"
                } else {
                   viewModel.updateEmail(email)
                    isSuccess = true
                    message = "Password Changed Successfully"
                    onPasswordChanged()


                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update email")
        }
    }
}
