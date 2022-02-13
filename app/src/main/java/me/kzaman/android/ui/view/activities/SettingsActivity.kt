package me.kzaman.android.ui.view.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import me.kzaman.android.base.BaseActivity
import me.kzaman.android.database.SharedPreferenceManager
import me.kzaman.android.databinding.ActivitySettingsBinding
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.viewModel.ProfileViewModel
import me.kzaman.android.utils.LoadingUtils
import me.kzaman.android.utils.handleActivityApiError
import me.kzaman.android.utils.startNewActivityAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.android.BuildConfig
import me.kzaman.android.utils.visible
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
        init()

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

    override fun init() {
        getUserProfile()
        viewModel.profile.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.tvName.text = it.value.data.toString()
                }
                is Resource.Failure -> handleActivityApiError(it) {
                    getUserProfile()
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

    override fun setToolbarTitle(title: String) {}

    private fun getUserProfile() {
        viewModel.getUserProfile()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            startNewActivityAnimation(DashboardActivity::class.java, false);
        }
    }
}