package com.example.mobile


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile.ui.screens.LoginScreen
import com.example.mobile.ui.screens.ProfileScreen
import com.example.mobile.ui.screens.RegisterScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val vm: MainViewModel = viewModel()
            val nav = rememberNavController()
            val state by vm.state.collectAsState()

            val start = if (!state.token.isNullOrBlank()) "profile" else "login"

            NavHost(navController = nav, startDestination = start) {
                composable("login") {
                    LoginScreen(
                        state = state,
                        onLogin = { email, pass -> vm.login(email, pass) },
                        goRegister = { nav.navigate("register") }
                    )
                    if (!state.token.isNullOrBlank()) nav.navigate("profile") {
                        popUpTo("login") { inclusive = true }
                    }
                }

                composable("register") {
                    RegisterScreen(
                        state = state,
                        onRegister = { e, u, p -> vm.register(e, u, p) },
                        goLogin = { nav.popBackStack() }
                    )
                }

                composable("profile") {
                    ProfileScreen(
                        state = state,
                        onRefresh = { vm.loadMe() },
                        onUpdateUsername = { vm.updateUsername(it) },
                        onLogout = {
                            vm.logout()
                            nav.navigate("login") {
                                popUpTo("profile") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
