package me.kzaman.demo_app.base

import me.kzaman.demo_app.network.BaseApi
import me.kzaman.demo_app.interfaces.SafeApiCall

abstract class BaseRepository(
    private val api: BaseApi,
) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}