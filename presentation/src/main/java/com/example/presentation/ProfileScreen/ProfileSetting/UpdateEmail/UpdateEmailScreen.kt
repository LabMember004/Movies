package com.example.presentation.ProfileScreen.ProfileSetting.UpdateEmail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun UpdateEmailScreen(viewModel: UpdateEmailViewModel = hiltViewModel(), navController: NavController) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Current Email") },
            modifier = Modifier.fillMaxWidth()
        )

        // Show the success message in green and error message in red
        if (message.isNotEmpty()) {
            val messageColor = if (message.startsWith("Success")) Color.Green else Color.Red
            Text(text = message, color = messageColor)
        }

        Button(
            onClick = {
                if (email.isNotEmpty()) {
                    // Call the ViewModel to update the email
                    viewModel.updateEmail(email) { isSuccess, responseMessage ->
                        if (isSuccess) {
                            message = "Success: Email successfully changed"
                            // Use the `navController` to navigate to the profile screen after update
                            navController.navigate("profile")
                        } else {
                            message = responseMessage
                        }
                    }
                } else {
                    message = "Email is required"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update email")
        }
    }
}



