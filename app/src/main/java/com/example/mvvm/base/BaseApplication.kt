package com.example.mvvm.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        lateinit var appInstance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}