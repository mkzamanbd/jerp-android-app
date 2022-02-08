package com.example.mvvm.di

import android.content.Context
import android.content.SharedPreferences
import com.example.mvvm.BuildConfig
import com.example.mvvm.network.api.AuthApi
import com.example.mvvm.network.api.CommonApi
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.network.NetworkHelper
import com.example.mvvm.utils.NoNetworkException
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePrefManager(@ApplicationContext mContext: Context): SharedPreferences =
        mContext.getSharedPreferences("MVVMPrefInfo", Context.MODE_PRIVATE)
    @Provides
    @Singleton
    fun provideSharedPref(preferences: SharedPreferences) = SharedPreferenceManager(preferences)

    @Provides
    fun provideBaseUrl() = "http://203.188.245.58:8889/api/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        helper: NetworkHelper,
        prefManager: SharedPreferenceManager,
    ): OkHttpClient {
        val interceptor = Interceptor { chain ->

            if (!helper.isNetworkConnected()) throw NoNetworkException()

            val original: Request = chain.request()
            val request: Request = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer ${prefManager.getAccessToken()}")
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .also { client ->
                if (BuildConfig.DEBUG) {
                    val loggIn = HttpLoggingInterceptor()
                    loggIn.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(loggIn)
                }
            }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideCommonApi(retrofit: Retrofit): CommonApi = retrofit.create(CommonApi::class.java)
}