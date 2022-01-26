package com.example.simplelogin.repository

import com.example.simplelogin.base.BaseRepository
import com.example.simplelogin.network.UserApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: UserApi,
) : BaseRepository(api) {

    suspend fun getUserProfile() = safeApiCall { api.getUserProfile() }
}