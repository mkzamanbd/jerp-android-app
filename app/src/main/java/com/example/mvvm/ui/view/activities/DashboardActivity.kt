package com.example.mvvm.ui.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.R
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.ui.viewModel.ProfileViewModel
import com.example.mvvm.utils.startNewActivity
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : BaseActivity() {
    @Inject
    lateinit var prefManager: SharedPreferenceManager
    private val viewModel by viewModels<ProfileViewModel>()

    lateinit var rlToolbar: RelativeLayout
    lateinit var tvTitle: TextView
    lateinit var tvBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        rlToolbar = findViewById(R.id.toolbar)
        tvTitle = findViewById(R.id.tv_toolbar_title)
        tvBack = findViewById(R.id.tv_back)
    }


    override fun init() {}
    override fun setToolbarTitle(title: String) {
        setToolbarTitle(title, tvTitle)
    }

    /**
     * ...hide toolbar
     */
    override fun hideToolbar() {
        rlToolbar.visibility = View.GONE
    }

    /**
     * ...show toolbar
     */
    override fun showToolbar(isBackButton: Boolean) {
        tvBack.visible(isBackButton)
        rlToolbar.visibility = View.VISIBLE
    }

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        prefManager.clearAll()
        startNewActivity(AuthActivity::class.java)
    }
}