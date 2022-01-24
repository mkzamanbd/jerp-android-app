package com.example.simplelogin.network

import com.example.simplelogin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object {
        private const val baseUrl = "https://api.kzaman.me/api/"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        authToken: String? = null,
    ): Api {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Authorization", "Bearer $authToken")
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