package com.example.basicapp.data.api

import com.example.basicapp.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("api/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 5
    ): UserResponse
}