package com.example.mvvm.repository

import com.example.mvvm.base.BaseRepository
import com.example.mvvm.network.UserApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: UserApi,
) : BaseRepository(api) {

    suspend fun getUserProfile() = safeApiCall { api.getUserProfile() }
}