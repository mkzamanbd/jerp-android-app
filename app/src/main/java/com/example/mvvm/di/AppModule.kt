package com.example.mvvm.di

import android.content.Context
import com.example.mvvm.network.AuthApi
import com.example.mvvm.network.RetrofitClient
import com.example.mvvm.network.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthApi(
        retrofitClient: RetrofitClient,
        @ApplicationContext context: Context,
    ): AuthApi {
        return retrofitClient.buildApi(AuthApi::class.java, context)
    }

    @Singleton
    @Provides
    fun provideUserApi(
        retrofitClient: RetrofitClient,
        @ApplicationContext context: Context,
    ): UserApi {
        return retrofitClient.buildApi(UserApi::class.java, context)
    }
}