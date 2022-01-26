package com.example.simplelogin.network

import android.content.Context
import com.example.simplelogin.BuildConfig
import com.example.simplelogin.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitClient @Inject constructor() {
    companion object {
        private const val baseUrl = "https://api.kzaman.me/api/"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context,
    ): Api {
        val userPreferences = UserPreferences(context)

        val accessToken = runBlocking {
            userPreferences.accessToken.first()
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(40, TimeUnit.SECONDS)
                    .writeTimeout(40, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Accept", "application/json")
                            it.addHeader("Authorization", "Bearer $accessToken")
                        }.build())
                    }
                    .also { client ->
                        if (BuildConfig.DEBUG) {
                            val loggIn = HttpLoggingInterceptor()
                            loggIn.setLevel(HttpLoggingInterceptor.Level.BODY)
                            client.addInterceptor(loggIn)
                        }
                    }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}