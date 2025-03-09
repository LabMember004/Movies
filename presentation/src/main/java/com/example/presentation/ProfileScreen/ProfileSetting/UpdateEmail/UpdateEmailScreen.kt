package com.example.presentation.ProfileScreen.ProfileSetting.UpdateEmail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UpdateEmailScreen(viewModel: UpdateEmailViewModel = hiltViewModel()) {
    var currentEmail by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }
    var confirmNewEmail by remember { mutableStateOf("") }

    val updateEmailResult by viewModel.updateEmailResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = currentEmail,
            onValueChange = { currentEmail = it },
            label = { Text("Current Email") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = newEmail,
            onValueChange = { newEmail = it },
            label = { Text("New Email") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = confirmNewEmail,
            onValueChange = { confirmNewEmail = it },
            label = { Text("Confirm New Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.updateEmail(newEmail) },
            modifier = Modifier.fillMaxWidth(),
            enabled = newEmail.isNotBlank() && newEmail == confirmNewEmail
        ) {
            Text("Update Email")
        }


        updateEmailResult.getOrNull()?.let {
            Text("Email updated successfully: ${it.message}", color = MaterialTheme.colorScheme.primary)
        } ?: updateEmailResult.exceptionOrNull()?.let {
            Text("Error: ${it.message}", color = MaterialTheme.colorScheme.error)
        }
    }
}
