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
import com.example.medicationapp.controller.rest.UserViewModel
import com.example.medicationapp.model.User
import com.example.medicationapp.model.repository.UserRepository
import com.example.medicationapp.repository.RoleRepository
import kotlinx.coroutines.launch

//@Composable
//fun SignupScreen(
//    onSignupSuccess: () -> Unit,
//    userViewModel: UserViewModel = viewModel()
//) {
//    val scope = rememberCoroutineScope()
//
//    var name by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }
//    var error by remember { mutableStateOf<String?>(null) }
//
//    val isLoading by userViewModel::isLoading
//    val status by userViewModel.status.collectAsState()
//
//    LaunchedEffect(status) {
//        status?.let {
//            if (it.fetchStatus() == "SUCCESS") {
//                onSignupSuccess()
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Sign Up", style = MaterialTheme.typography.headlineMedium)
//        Spacer(Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Full Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = confirmPassword,
//            onValueChange = { confirmPassword = it },
//            label = { Text("Confirm Password") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(16.dp))
//
//        val selectedRole = "Carer"
//        OutlinedTextField(
//            value = selectedRole,
//            onValueChange = {},
//            label = { Text("Role") },
//            readOnly = true,
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(Modifier.height(24.dp))
//
//        Button(
//            onClick = {
//                error = null
//                if (password != confirmPassword) {
//                    error = "Passwords do not match."
//                } else {
//                    val user = User(
//                        name = name,
//                        email = email,
//                        password = password,
//                        roleId = 2L
//                    )
//
//                    userViewModel.register(user)
//                }
//            },
//            modifier = Modifier.fillMaxWidth(),
//            enabled = !isLoading
//        ) {
//            Text("Create Account")
//        }
//
//        error?.let {
//            Spacer(Modifier.height(8.dp))
//            Text(it, color = Color.Red)
//        }
//
//        if (isLoading) {
//            Spacer(Modifier.height(8.dp))
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//        }
//    }
//}


@Composable
fun SignupScreen(
    context: Context,
    userViewModel: UserViewModel, // Pass UserViewModel
    onSignupSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

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

        val selectedRole = "Carer"
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
                        // Create a User object and pass it to register
                        val user = User(name = name, email = email, password = password, roleId = 2)
                        userViewModel.register(user) // Pass the User object
                        onSignupSuccess()
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
