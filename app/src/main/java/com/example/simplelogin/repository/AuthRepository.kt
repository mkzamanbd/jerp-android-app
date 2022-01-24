package com.example.simplelogin.repository

import com.example.simplelogin.data.UserPreferences
import com.example.simplelogin.network.AuthApi

class AuthRepository(
    private val authApi: AuthApi,
    private val userPreferences: UserPreferences
): BaseRepository() {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        authApi.login(email, password)
    }

    suspend fun saveAuthToken(token: String){
        userPreferences.saveAuthToken(token)
    }
}