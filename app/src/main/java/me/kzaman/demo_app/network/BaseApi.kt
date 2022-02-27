package me.kzaman.demo_app.network

import okhttp3.ResponseBody
import retrofit2.http.GET

interface BaseApi {
    @GET("logout")
    suspend fun logout(): ResponseBody
}