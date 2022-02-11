package me.kzaman.android.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import me.kzaman.android.R
import me.kzaman.android.database.SharedPreferenceManager
import me.kzaman.android.ui.view.activities.AuthActivity
import me.kzaman.android.ui.view.activities.DashboardActivity
import me.kzaman.android.utils.startNewActivityAnimation
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
                    startNewActivityAnimation(DashboardActivity::class.java)
                } else {
                    startNewActivityAnimation(AuthActivity::class.java)
                }
            },
            500,
        )
    }
}