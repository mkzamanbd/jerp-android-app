package me.kzaman.android.base

import me.kzaman.android.network.BaseApi
import me.kzaman.android.interfaces.SafeApiCall

abstract class BaseRepository(
    private val api: BaseApi,
) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}