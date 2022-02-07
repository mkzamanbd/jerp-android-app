package com.example.mvvm.data.response

import com.example.mvvm.data.model.UserDataModel


data class ProfileResponse(
    val code: Int,
    val status: String,
    val data: UserDataModel,
)