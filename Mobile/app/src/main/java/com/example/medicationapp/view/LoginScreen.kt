package com.example.medicationapp.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicationapp.controller.UserController
import com.example.medicationapp.controller.rest.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    context: Context,
    onLoginSuccess: (role: String, userId: Long) -> Unit,
    onNavigateToSignup: () -> Unit,
    viewModel: UserViewModel = viewModel()
) {
    val controller = remember { UserController(context) }
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val status by viewModel.status.collectAsState()

    LaunchedEffect(status) {
        status?.let {
            val user = controller.loginUser(email, password)
            if (it.getStatus() == "login" && user != null) {

                val roleName = controller.getRoleNameById(user.roleId)

                if (roleName != null) {
                    onLoginSuccess(roleName, user.userId)
                } else {
                    error = "User role not found"
                }
            } else {
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
                scope.launch {
                    viewModel.login(email,password)
                }
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
