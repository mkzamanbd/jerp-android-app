package com.example.mvvm.data.response

data class UserResponse(
    val success: Boolean,
    val users: List<ProfileResponse>
)