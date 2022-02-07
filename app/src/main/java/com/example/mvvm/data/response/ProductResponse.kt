package com.example.mvvm.data.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("message") val message: String,
    @SerializedName("product_list") val productList: List<Product>,
    @SerializedName("response_code") val responseCode: Int,
)

data class Product(
    @SerializedName("base_mrp") val baseMrp: String,
    @SerializedName("base_tp") val baseTp: String,
    @SerializedName("base_vat") val baseVat: String,
    @SerializedName("code_id") val codeId: Any,
    @SerializedName("com_pack_desc") val comPackDesc: String,
    @SerializedName("com_pack_size") val comPackSize: String,
    @SerializedName("display_code") val displayCode: String,
    @SerializedName("element") val element: List<Element>,
    @SerializedName("id") val id: Int,
    @SerializedName("min_qty") val minQty: Any,
    @SerializedName("mtp") val mtp: Int,
    @SerializedName("offer") val offer: String,
    @SerializedName("offer_type") val offerType: String,
    @SerializedName("prod_class") val prodClass: String,
    @SerializedName("prod_code") val prodCode: String,
    @SerializedName("prod_description") val prodDescription: String,
    @SerializedName("prod_id") val prodId: String,
    @SerializedName("prod_name") val prodName: String,
    @SerializedName("prod_offer_description") val prodOfferDescription: String,
    @SerializedName("search_keywords") val searchKeywords: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("status") val status: String,
    @SerializedName("uom") val uom: String,
    @SerializedName("valid_until") val validUntil: String,
)

data class Element(
    @SerializedName("code_id") val codeId: String,
    @SerializedName("element_name") val elementName: String,
    @SerializedName("id") val id: Int,
)