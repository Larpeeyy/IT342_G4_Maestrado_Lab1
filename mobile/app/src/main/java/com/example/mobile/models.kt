package com.example.mobile

data class RegisterRequest(val email: String, val username: String, val password: String)
data class LoginRequest(val email: String, val password: String)

// If your backend returns { "token": "..." }
data class AuthResponse(val token: String)

data class UserResponse(val id: Long, val email: String, val username: String)

// For username update
data class UpdateUsernameRequest(val username: String)
