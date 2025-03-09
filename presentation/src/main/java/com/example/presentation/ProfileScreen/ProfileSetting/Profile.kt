package com.example.presentation.ProfileScreen.ProfileSetting

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Profile(
    navController: NavController
) {

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("users+prefs" , Context.MODE_PRIVATE)

    val userEmail = remember {
        mutableStateOf(sharedPreferences.getString("USER_EMAIL" , "Unknown User")?: "Unknown User")
    }

    LaunchedEffect(userEmail.value) {
        userEmail.value = sharedPreferences.getString("USER_EMAIL", "Unknown User") ?: "Unknown user"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Profile", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))



            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Email: ${userEmail.value}")

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}