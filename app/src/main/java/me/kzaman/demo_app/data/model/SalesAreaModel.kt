package me.kzaman.demo_app.data.model

import com.google.gson.annotations.SerializedName

data class SalesAreaModel(
    @SerializedName("id") val areaId: String,
    @SerializedName("sales_area_id") val salesAreaId: String,
    @SerializedName("area_name") val areaName: String,
    @SerializedName("display_code") val displayCode: String,
)
