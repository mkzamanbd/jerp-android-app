package com.example.simplelogin.network

import android.content.Context
import com.example.simplelogin.data.UserPreferences
import com.example.simplelogin.data.response.TokenResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    context: Context
): Authenticator {

    private val appContext = context.applicationContext
    private val userPreferences = UserPreferences(appContext)
    override fun authenticate(route: Route?, response: Response): Request? {
        TODO("Not yet implemented")
    }
}