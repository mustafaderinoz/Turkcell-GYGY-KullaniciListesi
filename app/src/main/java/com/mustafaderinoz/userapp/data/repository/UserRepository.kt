package com.mustafaderinoz.userapp.data.repository

import com.mustafaderinoz.userapp.data.model.User
import com.mustafaderinoz.userapp.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}