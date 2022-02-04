package com.example.mvvm.ui.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.mvvm.R
import com.example.mvvm.data.UserPreferences
import com.example.mvvm.ui.viewModel.ProfileViewModel
import com.example.mvvm.utils.startNewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    @Inject
    lateinit var userPreferences: UserPreferences
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.userFragment,
                R.id.profileFragment,
                R.id.notificationFragment,
                R.id.settingsFragment
            )
        )
        setupActionBarWithNavController( navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        userPreferences.clear()
        startNewActivity(AuthActivity::class.java)
    }
}