package com.example.simplelogin.network

import com.example.simplelogin.data.response.ProfileResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("auth/current-user")
    suspend fun getUserProfile (): ProfileResponse

    @POST("auth/logout")
    suspend fun logout(): ResponseBody
}