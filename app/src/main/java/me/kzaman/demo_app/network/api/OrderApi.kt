package me.kzaman.demo_app.network.api

import me.kzaman.demo_app.data.response.DefaultResponse
import me.kzaman.demo_app.data.response.OrderResponse
import me.kzaman.demo_app.network.BaseApi
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.Path

interface OrderApi : BaseApi {

    @GET("common/v2/area-list-by-user/{customer_id}")
    suspend fun getAreaListByUser(@Path("customer_id") customerId: String): DefaultResponse

    @FormUrlEncoded
    @POST("mobile/create-order")
    suspend fun createOrder(
        @Field("sbu_id") sbuId: String,
        @Field("customer_id") customerId: String,
        @Field("sales_area_id") salesAreaId: String,
        @Field("order_note") orderNote: String,
        @Field("order_detail") orderDetail: String,
        @Field("delivery_address") deliveryAddress: String,
        @Field("date") orderDate: String,
    ): OrderResponse

}