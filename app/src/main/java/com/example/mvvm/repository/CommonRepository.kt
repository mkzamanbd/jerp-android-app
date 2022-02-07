package com.example.mvvm.repository

import com.example.mvvm.base.BaseRepository
import com.example.mvvm.api.CommonApi
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val api: CommonApi,
) : BaseRepository(api) {

    suspend fun getMobileMenu() = safeApiCall { api.getMobileMenu() }
    suspend fun getAllUsers() = safeApiCall { api.getAllUsers() }

    suspend fun getUserDetail(userId: String) = safeApiCall { api.getUserDetail(userId) }
}