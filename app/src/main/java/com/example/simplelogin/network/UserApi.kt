package com.example.simplelogin.network

import com.example.simplelogin.data.response.ProfileResponse
import retrofit2.http.GET

interface UserApi {

    @GET("auth/current-user")
    suspend fun getUserProfile (): ProfileResponse
}