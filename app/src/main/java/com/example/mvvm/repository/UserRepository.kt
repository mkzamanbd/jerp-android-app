package com.example.mvvm.repository

import com.example.mvvm.base.BaseRepository
import com.example.mvvm.api.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi,
) : BaseRepository(api) {

    suspend fun getAllUsers() = safeApiCall { api.getAllUsers() }

    suspend fun getUserDetail(userId: String) = safeApiCall { api.getUserDetail(userId) }
}