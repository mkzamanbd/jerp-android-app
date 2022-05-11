package me.kzaman.demo_app.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import me.kzaman.demo_app.base.BaseActivity
import me.kzaman.demo_app.database.SharedPreferenceManager
import me.kzaman.demo_app.databinding.ActivitySettingsBinding
import me.kzaman.demo_app.network.Resource
import me.kzaman.demo_app.ui.viewModel.ProfileViewModel
import me.kzaman.demo_app.utils.LoadingUtils
import me.kzaman.demo_app.utils.handleNetworkError
import me.kzaman.demo_app.utils.visible
import me.kzaman.demo_app.utils.startNewActivityAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.demo_app.BuildConfig
import me.kzaman.demo_app.R
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
        initializeApp()

        loadingUtils = LoadingUtils(this)

        val versionName = BuildConfig.VERSION_NAME
        binding.appVersion.text = versionName

        val ivBackButton = binding.toolbarRoot.ivBackButton

        binding.toolbarRoot.tvToolbarTitle.text = tvTitleStr
        ivBackButton.visible(true)

        ivBackButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun initializeApp() {
        getUserProfile()
        viewModel.profile.observe(this) {
            loadingUtils.isLoading(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.tvName.text = it.value.data.toString()
                }
                is Resource.Failure -> handleNetworkError(it, this) {
                    getUserProfile()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }

        binding.userLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                prefManager.clearAll()
                viewModel.clearAllTable()
                startNewActivityAnimation(AuthActivity::class.java)
            }
        }
    }

    override fun setToolbarTitle(title: String) {}

    private fun getUserProfile() {
        viewModel.getUserProfile()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
            overridePendingTransition(0, R.anim.animation_slide_out_right)
        }
    }
}