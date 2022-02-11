package com.example.android.network.api

import com.example.android.data.response.DefaultResponse
import com.example.android.data.response.MobileMenuResponse
import com.example.android.data.response.ProductResponse
import com.example.android.data.response.ProfileResponse
import com.example.android.network.BaseApi
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