package com.example.mvvm.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.mvvm.R
import com.example.mvvm.data.UserPreferences
import com.example.mvvm.ui.view.activities.AuthActivity
import com.example.mvvm.ui.view.activities.DashboardActivity
import com.example.mvvm.utils.startNewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userPreferences = UserPreferences(this)
        userPreferences.accessToken.asLiveData().observe(this, Observer {
            val activity =
                if (it == null) AuthActivity::class.java else DashboardActivity::class.java
            startNewActivity(activity)
        })
    }
}