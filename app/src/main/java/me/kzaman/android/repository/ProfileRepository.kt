package me.kzaman.android.repository

import me.kzaman.android.base.BaseRepository
import me.kzaman.android.network.api.CommonApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: CommonApi,
) : BaseRepository(api) {

    suspend fun getUserProfile() = safeApiCall { api.getUserProfile() }
}