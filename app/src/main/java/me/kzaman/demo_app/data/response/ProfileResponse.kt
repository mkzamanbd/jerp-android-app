package me.kzaman.demo_app.data.response

import me.kzaman.demo_app.data.model.UserDataModel


data class ProfileResponse(
    val code: Int,
    val status: String,
    val data: UserDataModel,
)