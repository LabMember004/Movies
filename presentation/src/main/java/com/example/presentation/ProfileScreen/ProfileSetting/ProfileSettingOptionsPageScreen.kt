package com.example.presentation.ProfileScreen.ProfileSetting

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.ProfileScreen.ProfileSetting.DeleteProfile.DeleteProfileViewModel
import com.example.presentation.navigation.Screen

@Composable
fun ProfileSettingOptionsPageScreen(
    navController: NavController,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToChangeEmail: () -> Unit,
    onLogout: () -> Unit,
    onDelete: () -> Unit,
    deleteProfileViewModel: DeleteProfileViewModel = hiltViewModel()
) {
    val deleteProfileResult = deleteProfileViewModel.deleteProfileResult.value
    val context = LocalContext.current

    val showConfirmationDialog = remember { mutableStateOf(false) }

    LaunchedEffect(deleteProfileResult) {
        deleteProfileResult?.let { result ->
            when {
                result.isSuccess -> {
                    Toast.makeText(context, "Profile deleted successfully", Toast.LENGTH_SHORT).show()
                    onDelete()
                }
                result.isFailure -> {
                    Toast.makeText(context, "Failed to delete profile: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ProfileSettingButton(
                text = "Update Email",
                icon = Icons.Filled.Email,
                onClick = { onNavigateToChangeEmail() }
            )

            ProfileSettingButton(
                text = "Update Password",
                icon = Icons.Filled.Lock,
                onClick = { onNavigateToChangePassword() }
            )

            ProfileSettingButton(
                text = "Delete Profile",
                icon = Icons.Filled.Delete,
                onClick = { showConfirmationDialog.value = true }
            )
        }

        Button(
            onClick = {
                onLogout()

                      },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            content = {
                Text("Logout")
            }
        )
    }

    if (showConfirmationDialog.value) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog.value = false },
            title = { Text("Delete Profile") },
            text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    deleteProfileViewModel.deleteProfile()
                    showConfirmationDialog.value = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmationDialog.value = false }) {
                    Text("No")
                }
            }
        )
    }
}
@Composable
fun ProfileSettingButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                tint = Color.White
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
