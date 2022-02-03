package com.example.mvvm.repository

import com.example.mvvm.base.BaseRepository
import com.example.mvvm.data.UserPreferences
import com.example.mvvm.api.AuthApi
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