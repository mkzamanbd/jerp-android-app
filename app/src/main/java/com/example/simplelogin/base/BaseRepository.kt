package com.example.simplelogin.base

import com.example.simplelogin.network.Resource
import com.example.simplelogin.network.SafeApiCall
import com.example.simplelogin.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository : SafeApiCall {
    suspend fun logout(api: UserApi) = safeApiCall {
        api.logout()
    }
}