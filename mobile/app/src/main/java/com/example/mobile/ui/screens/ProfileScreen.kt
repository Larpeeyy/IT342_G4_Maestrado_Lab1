package com.example.mobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.UiState

@Composable
fun ProfileScreen(
    state: UiState,
    onRefresh: () -> Unit,
    onUpdateUsername: (String) -> Unit,
    onLogout: () -> Unit
) {
    var newUsername by remember { mutableStateOf("") }

    Column(Modifier.padding(20.dp)) {
        Text("Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        val user = state.user
        if (user == null) {
            Text("No user loaded.")
            Spacer(Modifier.height(8.dp))
            Button(onClick = onRefresh, enabled = !state.loading) {
                Text(if (state.loading) "Loading..." else "Load Me")
            }
        } else {
            Text("Email: ${user.email}")
            Spacer(Modifier.height(6.dp))
            Text("Username: ${user.username}")

            Spacer(Modifier.height(14.dp))

            OutlinedTextField(
                value = newUsername,
                onValueChange = { newUsername = it },
                label = { Text("New Username") }
            )

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { onUpdateUsername(newUsername.trim()) },
                enabled = !state.loading
            ) { Text(if (state.loading) "Saving..." else "Save Username") }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) { Text("Logout") }
        }

        state.error?.let {
            Spacer(Modifier.height(10.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
