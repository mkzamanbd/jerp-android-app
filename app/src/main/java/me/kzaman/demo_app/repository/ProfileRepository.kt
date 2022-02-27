package me.kzaman.demo_app.repository

import me.kzaman.demo_app.base.BaseRepository
import me.kzaman.demo_app.network.api.CommonApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: CommonApi,
) : BaseRepository(api) {

    suspend fun getUserProfile() = safeApiCall { api.getUserProfile() }
}