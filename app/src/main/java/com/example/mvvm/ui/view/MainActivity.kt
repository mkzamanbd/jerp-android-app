package com.example.mvvm.ui.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm.R
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.ui.view.activities.AuthActivity
import com.example.mvvm.ui.view.activities.DashboardActivity
import com.example.mvvm.utils.startNewActivity

class MainActivity : AppCompatActivity() {
    private lateinit var spManager: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spManager = SharedPreferenceManager(applicationContext)

        Log.d("getIsUserLoggedIn", spManager.getIsUserLoggedIn().toString())

        if (spManager.getIsUserLoggedIn()) {
            startNewActivity(DashboardActivity::class.java)
        } else {
            startNewActivity(AuthActivity::class.java)
        }
    }
}