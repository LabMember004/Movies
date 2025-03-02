package com.example.presentation.ProfileScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfilePageScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirm Password") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.registerUser(name, email, password, confirmPassword)
        }) {
            Text("Register")
        }
        Spacer(modifier = Modifier.height(16.dp))

        viewModel.registerState.collectAsState().value?.let { result ->
            resultText = result.fold(
                onSuccess = { "Registration successful: ${'$'}it" },
                onFailure = { "Registration failed: ${'$'}it" }
            )
        }
        Text(resultText)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfilePageScreen() {
    ProfilePageScreen()
}
