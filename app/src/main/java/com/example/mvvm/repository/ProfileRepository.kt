package com.example.mvvm.repository

import com.example.mvvm.base.BaseRepository
import com.example.mvvm.api.CommonApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: CommonApi,
) : BaseRepository(api) {

    suspend fun getUserProfile() = safeApiCall { api.getUserProfile() }
}