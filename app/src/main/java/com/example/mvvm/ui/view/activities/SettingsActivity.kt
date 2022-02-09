package com.example.mvvm.ui.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.databinding.ActivitySettingsBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.ProfileViewModel
import com.example.mvvm.utils.handleActivityApiError
import com.example.mvvm.utils.startNewActivity
import com.example.mvvm.utils.visible
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

        binding.toolbarRoot.tvToolbarTitle.text = tvTitleStr
        binding.toolbarRoot.ivBackButton.visible(true)

        getUser()
        updateUI()

        viewModel.profile.observe(this) {
            binding.progressBar.visible(it is Resource.Loading)
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
                startNewActivity(AuthActivity::class.java)
            }
        }
    }

    override fun init() {}

    override fun setToolbarTitle(title: String) {}

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        binding.tvEmail.text =
            "Local=> Name ${prefManager.getUserFullName()}, Email: ${prefManager.getUserRoleName()}"
    }

    private fun getUser() {
        viewModel.getUser()
    }
}