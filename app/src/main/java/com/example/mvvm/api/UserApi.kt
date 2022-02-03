package com.example.mvvm.api

import com.example.mvvm.data.response.DefaultResponse
import com.example.mvvm.data.response.ProfileResponse
import com.example.mvvm.data.response.UserResponse
import com.example.mvvm.network.BaseApi
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi : BaseApi {

    @GET("auth/current-user")
    suspend fun getUserProfile(): ProfileResponse

    @GET("get-users")
    suspend fun getAllUsers(): UserResponse

    @GET("get-user-detail/{id}")
    suspend fun getUserDetail(
        @Path("id") id: String,
    ): DefaultResponse
}