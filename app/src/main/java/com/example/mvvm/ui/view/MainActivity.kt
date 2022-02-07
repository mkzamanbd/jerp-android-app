package com.example.mvvm.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm.R
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.ui.view.activities.AuthActivity
import com.example.mvvm.ui.view.activities.DashboardActivity
import com.example.mvvm.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var prefManager: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (prefManager.getIsUserLoggedIn()) {
            startNewActivity(DashboardActivity::class.java)
        } else {
            startNewActivity(AuthActivity::class.java)
        }
    }
}