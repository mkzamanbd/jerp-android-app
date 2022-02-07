package com.example.mvvm.di

import android.content.Context
import android.content.SharedPreferences
import com.example.mvvm.api.AuthApi
import com.example.mvvm.network.RetrofitClient
import com.example.mvvm.api.CommonApi
import com.example.mvvm.database.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthApi(
        retrofitClient: RetrofitClient,
        @ApplicationContext context: Context,
    ): AuthApi {
        return retrofitClient.buildApi(AuthApi::class.java, context)
    }

    @Provides
    @Singleton
    fun provideUserApi(
        retrofitClient: RetrofitClient,
        @ApplicationContext context: Context,
    ): CommonApi {
        return retrofitClient.buildApi(CommonApi::class.java, context)
    }

    @Provides
    @Singleton
    fun providePrefManager(@ApplicationContext mContext: Context): SharedPreferences = mContext.getSharedPreferences("MVVMPrefInfo", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPref(preferences : SharedPreferences) = SharedPreferenceManager(preferences)
}