package com.example.simplelogin.base

import com.example.simplelogin.network.BaseApi
import com.example.simplelogin.network.Resource
import com.example.simplelogin.network.SafeApiCall
import com.example.simplelogin.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository(
    private val api: BaseApi,
) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}