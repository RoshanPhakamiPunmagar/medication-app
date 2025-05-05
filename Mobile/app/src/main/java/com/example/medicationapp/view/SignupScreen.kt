package com.example.medicationapp.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.viewmodel.UserViewModel
import com.example.medicationapp.model.User
import com.example.medicationapp.model.repository.UserRepository
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    context: Context,
    onSignupSuccess: () -> Unit,
    viewModel: UserViewModel = viewModel()
) {
    val controller = remember { UserRepository(context) }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val status by viewModel.status.collectAsState()

    // Fixed role as "Carer"
    val selectedRole = "Carer"
    LaunchedEffect(status) {
        status.let {
            val result = controller.registerUser(name, email, password, selectedRole)

            if (it?.getStatus() == "register" && result.isSuccess) {
                onSignupSuccess()
            } else {
                error = result.exceptionOrNull()?.message ?: "Signup failed. Try again."
            }
        }
    }
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

        // Display the fixed role "Carer"
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

                when {
                    password != confirmPassword -> error = "Passwords do not match."
                    else -> {
                        scope.launch {
                            val user = User(
                                name = name,
                                email = email,
                                password = password,
                                roleId = 2
                            )
                            viewModel.register(user)
                        }
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