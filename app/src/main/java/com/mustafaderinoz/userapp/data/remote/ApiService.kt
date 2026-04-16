package com.mustafaderinoz.userapp.data.remote

import com.mustafaderinoz.userapp.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}