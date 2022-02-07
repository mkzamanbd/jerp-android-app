package com.example.mvvm.data.response

import com.example.mvvm.data.model.TokenDataModel
import com.example.mvvm.data.model.UserDataModel
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code") var responseCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") var data: DataModel,
)

data class DataModel(
    val token: TokenDataModel,
    val user: UserDataModel,
)