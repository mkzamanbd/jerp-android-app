package me.kzaman.demo_app.data.response

import com.google.gson.annotations.SerializedName
import me.kzaman.demo_app.data.model.CustomerModel

data class DefaultResponse(
    @SerializedName("response_code") val responseCode: Int?,
    @SerializedName("message") val message: String,
    @SerializedName("customer_list") var customerList: List<CustomerModel>,
)
