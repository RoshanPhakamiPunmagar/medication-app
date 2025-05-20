package com.example.medicationapp.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    context: Context,
    onSignupSuccessNavigateToLogin: () -> Unit,
    viewModel: UserViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    var showVerificationMessage by remember { mutableStateOf(false) }
    var hasNavigated by remember { mutableStateOf(false) }

    val status by viewModel.status.collectAsState()
    val selectedRole = "Carer"

    // Handle signup status
    LaunchedEffect(status) {
        status?.let {
            if (it.status == "register" && !hasNavigated) {
                showVerificationMessage = true
            } else if (it.status == "exists") {
                Toast.makeText(context, "Email already taken. Try another.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Navigate after showing verification message
    LaunchedEffect(showVerificationMessage) {
        if (showVerificationMessage && !hasNavigated) {
            kotlinx.coroutines.delay(2000)
            hasNavigated = true
            onSignupSuccessNavigateToLogin()
        }
    }

    if (showVerificationMessage) {
        // Success message screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Signup successful!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    } else {
        // Signup form screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Sign Up", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

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
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = selectedRole,
                onValueChange = {},
                label = { Text("Role") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    error = null
                    val isFormValid = name.isNotBlank() &&
                            email.isNotBlank() &&
                            password.isNotBlank() &&
                            confirmPassword.isNotBlank()

                    if (!isFormValid) {
                        error = "Please fill in all fields."
                    } else if (password != confirmPassword) {
                        error = "Passwords do not match."
                    } else {
                        scope.launch {
                            viewModel.register(name, email, password)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Account")
            }

            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = Color.Red)
            }
        }
    }
}
