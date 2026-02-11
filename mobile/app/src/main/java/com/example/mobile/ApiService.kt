package com.example.mobile

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("/api/auth/register")
    suspend fun register(@Body body: RegisterRequest)

    @POST("/api/auth/login")
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @GET("/api/users/me")
    suspend fun me(): UserResponse

    @PUT("/api/users/username")
    suspend fun updateUsername(@Body body: UpdateUsernameRequest): UserResponse
}
