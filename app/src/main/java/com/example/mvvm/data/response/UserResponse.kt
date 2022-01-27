package com.example.mvvm.data.response

import com.example.mvvm.data.model.User

data class UserResponse(
    val success: Boolean,
    val users: List<User>
)