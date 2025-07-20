package com.example.basicapp.data.repository

import com.example.basicapp.data.api.RetrofitClient
import com.example.basicapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserRepository {
    private val apiService = RetrofitClient.userApiService

    suspend fun getUsers(page: Int, perPage: Int = 5): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getUsers(page, perPage)
                response.data
            } catch (e: Exception) {
                throw e
            }
        }
    }
}