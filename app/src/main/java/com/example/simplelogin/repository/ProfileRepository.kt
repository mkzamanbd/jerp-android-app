package com.example.simplelogin.repository

import com.example.simplelogin.network.UserApi

class ProfileRepository(
    private val api: UserApi,
): BaseRepository() {

    suspend fun getUserProfile() = safeApiCall {
        api.getUserProfile()
    }
}