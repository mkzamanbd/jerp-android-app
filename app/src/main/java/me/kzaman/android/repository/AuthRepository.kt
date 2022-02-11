package me.kzaman.android.repository

import me.kzaman.android.base.BaseRepository
import me.kzaman.android.network.api.AuthApi
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