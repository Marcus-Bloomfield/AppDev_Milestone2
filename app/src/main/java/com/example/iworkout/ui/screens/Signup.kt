package com.example.iworkout.ui.screens
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iworkout.data.model.User
import com.example.iworkout.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun SignUp(
    onLoginNavigate: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope() // For showing snackbar

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Sign Up", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Validate input fields
                    if (name.isBlank() || username.isBlank() || password.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Please fill all fields")
                        }
                        return@Button
                    }

                    // Sign up the user
                    val user = User(UUID.randomUUID().toString(),username = username, password = password, name = name)
                    authViewModel.signUp(user) { success ->
                        Log.d("SignUp", "Sign-up success: $success")
                        if (success) {

                            onLoginNavigate()
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Sign up successful!")

                            }

                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Sign up failed. Try again.")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Sign Up")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { onLoginNavigate() }) {
                Text("Already have an account? Login")
            }
        }
    }
}
