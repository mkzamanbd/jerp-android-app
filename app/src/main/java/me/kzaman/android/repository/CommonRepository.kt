package me.kzaman.android.repository

import me.kzaman.android.base.BaseRepository
import me.kzaman.android.network.api.CommonApi
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val api: CommonApi,
) : BaseRepository(api) {

    suspend fun getMobileMenu() = safeApiCall { api.getMobileMenu() }
    suspend fun getAllProducts() = safeApiCall { api.getAllProducts() }

    suspend fun getUserDetail(userId: String) = safeApiCall { api.getUserDetail(userId) }
}