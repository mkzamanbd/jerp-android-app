package com.example.mvvm.api

import com.example.mvvm.data.response.DefaultResponse
import com.example.mvvm.data.response.MobileMenuResponse
import com.example.mvvm.data.response.ProductResponse
import com.example.mvvm.data.response.ProfileResponse
import com.example.mvvm.network.BaseApi
import retrofit2.http.GET
import retrofit2.http.Path

interface CommonApi : BaseApi {

    @GET("user")
    suspend fun getUserProfile(): ProfileResponse

    @GET("mobile/mobile-menu")
    suspend fun getMobileMenu(): MobileMenuResponse

    @GET("web/v2/search-product-data-list")
    suspend fun getAllProducts(): ProductResponse

    @GET("get-user-detail/{id}")
    suspend fun getUserDetail(
        @Path("id") id: String,
    ): DefaultResponse
}