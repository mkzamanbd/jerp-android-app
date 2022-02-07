package com.example.mvvm.data.response

import com.example.mvvm.data.model.TokenDataModel
import com.example.mvvm.data.model.UserDataModel

data class LoginResponse(
    val code: Int,
    val message: String,
    val data: DataModel,
)

data class DataModel(
    val token: TokenDataModel,
    val user: UserDataModel,
)