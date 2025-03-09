package com.example.presentation.ProfileScreen.Login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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



    val loginState = viewModel.registerState.collectAsState()

    val context = LocalContext.current
    val tokenState by viewModel.tokenState.collectAsState()

    LaunchedEffect(loginState.value) {
        loginState.value?.onSuccess {
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

            val sharedPreferences = context.getSharedPreferences("users+prefs" , Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("USER_EMAIL" , email)
            editor.apply()
        }
        loginState.value?.onFailure {
            Toast.makeText(context, "Login Failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(tokenState) {
        tokenState?.let {
            if(it.isNotEmpty()) {
                Toast.makeText(context, "User Already Logged in" , Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login")

        Spacer(modifier = Modifier.height(16.dp))

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

        Button(
            onClick = {
                viewModel.loginUser(email, password)
                onLoginSuccess()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        Text("Dont have an account?")

        Text(
            text= "Register",
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                onNavigateToRegister()
            }
        )
    }
}
