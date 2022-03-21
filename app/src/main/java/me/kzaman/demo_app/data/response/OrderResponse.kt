package me.kzaman.demo_app.data.response

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("response_code") val responseCode: String?,
    @SerializedName("message") val message: String,
)
