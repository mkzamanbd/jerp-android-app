package com.example.simplelogin.data.response

import com.example.simplelogin.data.model.User

data class LoginResponse(
    val message: String,
    val response_code: Int,
    val token: String,
    val user: User,
)