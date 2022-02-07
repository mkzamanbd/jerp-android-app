package com.example.mvvm.data.response


data class ProfileResponse(
    val code: Int,
    val status: String,
    val data: UserDataModel,
)