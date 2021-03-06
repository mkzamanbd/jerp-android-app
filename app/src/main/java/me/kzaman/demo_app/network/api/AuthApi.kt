package me.kzaman.demo_app.network.api

import me.kzaman.demo_app.data.response.LoginResponse
import me.kzaman.demo_app.network.BaseApi
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi : BaseApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
    ): LoginResponse
}