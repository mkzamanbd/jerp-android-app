package com.example.mvvm.base

import com.example.mvvm.network.BaseApi
import com.example.mvvm.interfaces.SafeApiCall

abstract class BaseRepository(
    private val api: BaseApi,
) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}