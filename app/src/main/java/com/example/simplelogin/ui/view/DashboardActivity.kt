package com.example.simplelogin.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.simplelogin.R
import com.example.simplelogin.data.UserPreferences
import com.example.simplelogin.ui.view.auth.AuthActivity
import com.example.simplelogin.ui.viewModel.ProfileViewModel
import com.example.simplelogin.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private val viewModel by viewModels<ProfileViewModel>()
    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        userPreferences.clear()
        startNewActivity(AuthActivity::class.java)
    }
}