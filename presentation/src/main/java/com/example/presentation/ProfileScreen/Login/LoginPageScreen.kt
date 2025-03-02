package com.example.presentation.ProfileScreen.Login

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginPageScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    // Remembering the state of the email and password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observe login state
    val loginState = viewModel.registerState.collectAsState()

    // Get context for displaying Toasts
    val context = LocalContext.current

    // Observe loginState and show a message based on success/failure
    LaunchedEffect(loginState.value) {
        loginState.value?.onSuccess {
            // Handle login success (e.g., show Toast)
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
        }
        loginState.value?.onFailure {
            // Handle login failure (e.g., show error message)
            Toast.makeText(context, "Login Failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // UI for the login screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login")

        Spacer(modifier = Modifier.height(16.dp))

        // Email input
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.Gray),
            decorationBox = { innerTextField ->
                if (email.isEmpty()) {
                    Text("Email", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.Gray),
            decorationBox = { innerTextField ->
                if (password.isEmpty()) {
                    Text("Password", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                // Call loginUser when the button is pressed
                viewModel.loginUser(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }
}
