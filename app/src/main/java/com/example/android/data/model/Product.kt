package com.example.android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @Expose
    @SerializedName("prod_id") val productId: String,
    @SerializedName("prod_class") val productClass: String,
    @SerializedName("base_tp") val baseTp: Double = 0.0,
    @SerializedName("base_qty") val baseQty: Int = 0,
    @SerializedName("base_vat") val baseVat: Double = 0.0,
    @SerializedName("prod_name") val productName: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("prod_code") val productCode: String,
    @SerializedName("display_code") val displayCode: String,
    @SerializedName("search_keywords") val searchKey: String,
    @SerializedName("com_pack_size") val packSize: String? = null,
    @SerializedName("com_pack_desc") val comPackDesc: String? = null,
    @SerializedName("prod_offer_description") val offerDescription: String? = null,
    @SerializedName("mtp") val mtp: Double = 0.0,
    @SerializedName("offer") val offer: String? = null,
    @SerializedName("offer_type") val offerType: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("valid_until") val validUntil: String,
    @SerializedName("min_qty") val minimumQty: Int = 0,
    @SerializedName("element") val elementInfo: ArrayList<Element>? = null,

    @Expose
    val quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val elements: String,
)

data class Element(
    @SerializedName("code_id") val codeId: String,
    @SerializedName("element_name") val elementName: String,
    @SerializedName("id") val id: Int,
)