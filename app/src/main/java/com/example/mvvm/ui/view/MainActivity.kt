package com.example.mvvm.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
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

    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.m_progress_bar)

        Handler(Looper.getMainLooper()).postDelayed(
            { //This method will be executed once the timer is over
                if (prefManager.getIsUserLoggedIn()) {
                    startNewActivity(DashboardActivity::class.java)
                    overridePendingTransition(R.anim.animation_fade_in, R.anim.animation_fade_out)
                } else {
                    startNewActivity(AuthActivity::class.java)
                    overridePendingTransition(R.anim.animation_fade_in, R.anim.animation_fade_out)
                }
            },
            1000,
        )
    }
}