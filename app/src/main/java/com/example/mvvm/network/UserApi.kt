package com.example.mvvm.network

import com.example.mvvm.data.response.ProfileResponse
import retrofit2.http.GET

interface UserApi : BaseApi {
    @GET("auth/current-user")
    suspend fun getUserProfile(): ProfileResponse
}