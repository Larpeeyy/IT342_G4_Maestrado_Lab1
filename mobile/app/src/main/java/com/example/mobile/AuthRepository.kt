package com.example.mobile

class AuthRepository(
    private val api: ApiService,
    private val tokenStore: TokenStore
) {
    suspend fun register(email: String, username: String, password: String) {
        api.register(RegisterRequest(email, username, password))
    }

    suspend fun login(email: String, password: String) {
        val res = api.login(LoginRequest(email, password))
        tokenStore.saveToken(res.token)
    }

    suspend fun me(): UserResponse = api.me()

    suspend fun updateUsername(username: String): UserResponse =
        api.updateUsername(UpdateUsernameRequest(username))

    suspend fun logout() {
        tokenStore.clearToken()
    }
}
