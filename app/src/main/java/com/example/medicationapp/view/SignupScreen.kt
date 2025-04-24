package com.example.medicationapp.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.medicationapp.controller.UserController
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    context: Context,
    onSignupSuccess: () -> Unit  // Add necessary parameters here
) {
    val controller = remember { UserController(context) }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var roles by remember { mutableStateOf(listOf<String>()) }
    var selectedRole by remember { mutableStateOf("") }
    var dropdownOpen by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    // Load roles once
    LaunchedEffect(Unit) {
        controller.preloadRoles()
        roles = controller.getRoles()
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

        // Role dropdown - Using a basic implementation
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedRole,
                onValueChange = {},
                label = { Text("Select Role") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            IconButton(
                onClick = { dropdownOpen = !dropdownOpen },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown"
                )
            }

            DropdownMenu(
                expanded = dropdownOpen,
                onDismissRequest = { dropdownOpen = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                roles.forEach { role ->
                    DropdownMenuItem(
                        text = { Text(role) },
                        onClick = {
                            selectedRole = role
                            dropdownOpen = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                error = null

                // Validation
                when {
                    password != confirmPassword -> error = "Passwords do not match."
                    selectedRole.isBlank() -> error = "Please select a role."
                    else -> {
                        scope.launch {
                            val result = controller.registerUser(
                                name, email, password, selectedRole
                            )
                            if (result.isSuccess) {
                                onSignupSuccess()
                            } else {
                                error = result.exceptionOrNull()?.message ?: "Signup failed. Try again."
                            }
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
