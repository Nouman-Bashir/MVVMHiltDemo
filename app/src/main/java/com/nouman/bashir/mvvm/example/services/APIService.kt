package com.nouman.bashir.mvvm.example.services

import com.nouman.bashir.mvvm.example.models.User
import retrofit2.http.GET

interface APIService {
    @GET("users")
    suspend fun getUsers():List<User>
}