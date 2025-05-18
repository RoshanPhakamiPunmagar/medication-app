package com.example.medicationapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.util.TokenManager
import com.example.medicationapp.viewmodel.UserViewModel


@Composable
fun LoginScreen(
    onLoginSuccess: (role: String, userId: Long) -> Unit,
    onNavigateToSignup: () -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val status by userViewModel.status.collectAsState()
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }

    // Inject TokenManager into ViewModel after it's created
    LaunchedEffect(Unit) {
        userViewModel.setTokenManager(tokenManager)
    }

    LaunchedEffect(status) {
        status?.let {
            if (it.status == "login") {
                val roleName = when (it.roleId) {
                    1L -> "Manager"
                    2L -> "Carer"
                    else -> "Unknown"
                }

                it.userId?.let { id ->
                    onLoginSuccess(roleName, id)
                } ?: run {
                    error = "Login failed: Missing user ID"
                }
            } else if (it.status == "invalid") {
                error = "Invalid email or password"
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                error = null
                userViewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }

        TextButton(
            onClick = onNavigateToSignup,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Don't have an account? Sign up")
        }

        error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = Color.Red)
        }
    }
}
