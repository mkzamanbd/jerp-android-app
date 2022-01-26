package com.example.mvvm.network

import com.example.mvvm.data.response.ProfileResponse
import com.example.mvvm.data.response.UserResponse
import retrofit2.http.GET

interface UserApi : BaseApi {

    @GET("auth/current-user")
    suspend fun getUserProfile(): ProfileResponse

    @GET("get-users")
    suspend fun getAllUsers(): UserResponse
}