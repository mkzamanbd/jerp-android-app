package me.kzaman.demo_app.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CartItemsModel(
    @Expose @SerializedName("id") var productId: String,
    @Expose @SerializedName("qty") var quantity: Int = 0,
)
