package com.example.simplelogin.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.simplelogin.R
import com.example.simplelogin.data.UserPreferences
import com.example.simplelogin.ui.view.auth.AuthActivity
import com.example.simplelogin.utils.startNewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userPreferences = UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this, Observer {
            val activity = if(it == null) AuthActivity::class.java else DashboardActivity::class.java
            startNewActivity(activity)
        })
    }
}