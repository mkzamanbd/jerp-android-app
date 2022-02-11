package me.kzaman.android.network.api

import me.kzaman.android.data.response.DefaultResponse
import me.kzaman.android.data.response.MobileMenuResponse
import me.kzaman.android.data.response.ProductResponse
import me.kzaman.android.data.response.ProfileResponse
import me.kzaman.android.network.BaseApi
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