package me.kzaman.android.data.response

import me.kzaman.android.data.model.ProductInfo
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("message") val message: String,
    @SerializedName("product_list") val productList: List<ProductInfo>,
    @SerializedName("response_code") val responseCode: Int,
)