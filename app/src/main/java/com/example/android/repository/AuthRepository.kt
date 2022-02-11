package com.example.android.repository

import com.example.android.base.BaseRepository
import com.example.android.network.api.AuthApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
) : BaseRepository(api) {

    suspend fun login(
        userName: String,
        password: String,
    ) = safeApiCall {
        api.login(userName, password)
    }
}