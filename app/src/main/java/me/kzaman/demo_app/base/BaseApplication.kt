package me.kzaman.demo_app.base

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