package com.example.presentation.ProfileScreen.Register

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ProfilePageScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onRegisterSuccessful: () -> Unit
) {

    var resultText by remember { mutableStateOf("") }
    var isRegistrationSuccessful by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    var isRegistering by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.uiState.collect { uiState ->
            if (uiState.isSuccess) {
                resultText = "Registration successful"
                isRegistrationSuccessful = true
                isRegistering = false
                onRegisterSuccessful()
            } else if (uiState.errorMessage != null) {
                resultText = "Registration failed: ${uiState.errorMessage}"
                isRegistrationSuccessful = false
                isRegistering = false
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF6A1B9A), Color(0xFF8E24AA))))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8E24AA)
            )

            OutlinedTextField(
                value = uiState.username,
                onValueChange = viewModel::updateUsername,
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange =  viewModel::updateEmail ,
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::updatePassword,
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = viewModel::updateConfirmPassword,
                label = { Text("Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!isRegistering) {
                        isRegistering = true
                        viewModel.onClickRegister()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8E24AA))
            ) {
                Text("Register", color = Color.White)
            }


            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = resultText,
                color = if (isRegistrationSuccessful) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Already have an account?",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Login",
                color = Color(0xFF8E24AA),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { onNavigateToRegister() }
                    .padding(top = 8.dp)
            )
        }
    }
}
