package com.example.mvvm.ui.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm.R
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.ui.viewModel.ProfileViewModel
import com.example.mvvm.utils.startNewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : BaseActivity() {
    @Inject
    lateinit var prefManager: SharedPreferenceManager
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
                R.id.dashboardFragment,
                R.id.userFragment,
                R.id.profileFragment,
                R.id.notificationFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

    }


    override fun init() {}
    override fun setToolbarTitle(title: String) {}

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        prefManager.clearAll()

        Toast.makeText(applicationContext, "User Successfully Logged Out", Toast.LENGTH_SHORT).show()
        startNewActivity(AuthActivity::class.java)
    }
}