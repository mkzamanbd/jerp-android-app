package me.kzaman.android.data.response

import me.kzaman.android.data.model.UserDataModel


data class ProfileResponse(
    val code: Int,
    val status: String,
    val data: UserDataModel,
)