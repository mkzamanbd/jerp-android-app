package com.example.simplelogin.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.simplelogin.R
import com.example.simplelogin.data.UserPreferences
import com.example.simplelogin.ui.view.auth.AuthActivity
import com.example.simplelogin.ui.viewModel.ProfileViewModel
import com.example.simplelogin.utils.startNewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.navigation.ui.NavigationUI
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
        bottomNavigationView.setupWithNavController(navController)

//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.userFragment,
//            R.id.profileFragment,
//            R.id.notificationFragment,
//            R.id.settingsFragment
//        ).build()
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        userPreferences.clear()
        startNewActivity(AuthActivity::class.java)
    }
}