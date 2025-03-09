package com.example.presentation.ProfileScreen.Login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LoginPageScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState = viewModel.loginState.collectAsState()
    val context = LocalContext.current
    val tokenState by viewModel.tokenState.collectAsState()

    LaunchedEffect(loginState.value) {
        loginState.value?.onSuccess {
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

            val sharedPreferences = context.getSharedPreferences("users+prefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("USER_EMAIL", email)
            editor.apply()

            onLoginSuccess()
        }
        loginState.value?.onFailure {
            Toast.makeText(context, "Login Failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(tokenState) {
        tokenState?.let {
            if (it.isNotEmpty()) {
                Toast.makeText(context, "User Already Logged in", Toast.LENGTH_SHORT).show()
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
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8E24AA)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
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
                    viewModel.loginUser(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8E24AA))
            ) {
                Text("Login", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Don't have an account?",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Register",
                color = Color(0xFF8E24AA),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { onNavigateToRegister() }
                    .padding(top = 8.dp)
            )
        }
    }
}
