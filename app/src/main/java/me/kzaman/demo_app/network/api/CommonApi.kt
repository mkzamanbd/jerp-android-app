package me.kzaman.demo_app.network.api

import me.kzaman.demo_app.data.response.DefaultResponse
import me.kzaman.demo_app.data.response.MobileMenuResponse
import me.kzaman.demo_app.data.response.ProductResponse
import me.kzaman.demo_app.data.response.ProfileResponse
import me.kzaman.demo_app.network.BaseApi
import retrofit2.http.GET

interface CommonApi : BaseApi {

    @GET("user")
    suspend fun getUserProfile(): ProfileResponse

    @GET("mobile/mobile-menu")
    suspend fun getMobileMenu(): MobileMenuResponse

    @GET("web/v2/search-product-data-list")
    suspend fun getRemoteProducts(): ProductResponse

    @GET("mobile/get-sales-user-wise-all-customer-list")
    suspend fun getAllCustomers(): DefaultResponse
}