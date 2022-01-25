package com.example.simplelogin.network

import com.example.simplelogin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val baseUrl = "https://api.kzaman.me/api/"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        accessToken: String? = null,
    ): Api {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
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