package com.example.android.data.response

import com.example.android.data.model.SalesAreaModel
import com.example.android.data.model.TokenDataModel
import com.example.android.data.model.UserDataModel
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code") var responseCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") var data: DataModel,
)

data class DataModel(
    @SerializedName("token") val token: TokenDataModel,
    @SerializedName("user") val user: UserDataModel? = null,
    @SerializedName("user_area") val userArea: SalesAreaModel? = null
)