package com.example.mobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.UiState

@Composable
fun LoginScreen(
    state: UiState,
    onLogin: (String, String) -> Unit,
    goRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(Modifier.padding(20.dp)) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Password") })

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { onLogin(email.trim(), pass) },
            enabled = !state.loading
        ) { Text(if (state.loading) "Logging in..." else "Login") }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = goRegister) { Text("Go to Register") }

        state.error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
