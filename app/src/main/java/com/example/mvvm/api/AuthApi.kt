package com.example.mvvm.api

import com.example.mvvm.data.response.LoginResponse
import com.example.mvvm.network.BaseApi
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi : BaseApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String,
    ): LoginResponse
}