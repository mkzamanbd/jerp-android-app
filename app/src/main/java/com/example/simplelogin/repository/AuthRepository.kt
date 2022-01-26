package com.example.simplelogin.repository

import com.example.simplelogin.base.BaseRepository
import com.example.simplelogin.data.UserPreferences
import com.example.simplelogin.network.AuthApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val userPreferences: UserPreferences,
) : BaseRepository(api) {

    suspend fun login(
        email: String,
        password: String,
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAccessToken(token: String) {
        userPreferences.saveAccessToken(token)
    }
}