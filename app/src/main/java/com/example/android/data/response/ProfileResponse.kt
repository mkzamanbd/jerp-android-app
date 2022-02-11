package com.example.android.data.response

import com.example.android.data.model.UserDataModel


data class ProfileResponse(
    val code: Int,
    val status: String,
    val data: UserDataModel,
)