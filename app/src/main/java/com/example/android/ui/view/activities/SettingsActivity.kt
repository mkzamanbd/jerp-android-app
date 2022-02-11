package com.example.android.ui.view.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android.BuildConfig
import com.example.android.base.BaseActivity
import com.example.android.database.SharedPreferenceManager
import com.example.android.databinding.ActivitySettingsBinding
import com.example.android.network.Resource
import com.example.android.ui.viewModel.ProfileViewModel
import com.example.android.utils.LoadingUtils
import com.example.android.utils.handleActivityApiError
import com.example.android.utils.startNewActivityAnimation
import com.example.android.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : BaseActivity() {
    @Inject
    lateinit var prefManager: SharedPreferenceManager
    private val viewModel by viewModels<ProfileViewModel>()
    private lateinit var binding: ActivitySettingsBinding

    private val tvTitleStr: String = "Settings"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadingUtils = LoadingUtils(this)

        val versionName = BuildConfig.VERSION_NAME
        binding.appVersion.text = versionName

        val ivBackButton = binding.toolbarRoot.ivBackButton

        binding.toolbarRoot.tvToolbarTitle.text = tvTitleStr
        ivBackButton.visible(true)

        ivBackButton.setOnClickListener {
            onBackPressed()
        }

        init()

        viewModel.profile.observe(this) {
            loadingUtils.isLoading(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.tvName.text = it.value.data.toString()
                }
                is Resource.Failure -> handleActivityApiError(it) {
                    getUser()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }

        binding.userLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                prefManager.clearAll()
                startNewActivityAnimation(AuthActivity::class.java)
            }
        }
    }

    override fun init() {
        getUser()
    }

    override fun setToolbarTitle(title: String) {}

    private fun getUser() {
        viewModel.getUser()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            startNewActivityAnimation(DashboardActivity::class.java, false);
        }
    }
}