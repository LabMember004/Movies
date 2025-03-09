package com.example.presentation.ProfileScreen.Register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ProfilePageScreen(viewModel: RegisterViewModel = hiltViewModel() , onNavigateToRegister : () -> Unit, onRegisterSuccessful: () -> Unit) {
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
            onRegisterSuccessful()
        }) {
            Text("Register")
        }

        Text(
            text = "Login" ,
            color = Color.Blue,
            modifier = Modifier.clickable {
                onNavigateToRegister()
            }
        )
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


