package me.kzaman.demo_app.repository

import me.kzaman.demo_app.base.BaseRepository
import me.kzaman.demo_app.network.api.AuthApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
) : BaseRepository(api) {

    suspend fun login(
        userName: String,
        password: String,
    ) = safeApiCall {
        api.login(userName, password)
    }
}