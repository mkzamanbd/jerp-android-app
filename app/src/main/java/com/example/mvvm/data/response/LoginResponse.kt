package com.example.mvvm.data.response

import com.example.mvvm.data.model.User

data class LoginResponse(
    val message: String,
    val response_code: Int,
    val token: String,
    val user: User,
)