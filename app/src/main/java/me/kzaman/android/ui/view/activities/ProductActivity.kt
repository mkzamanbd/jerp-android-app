package me.kzaman.android.ui.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import me.kzaman.android.R
import me.kzaman.android.base.BaseActivity
import me.kzaman.android.databinding.ActivityProductBinding
import me.kzaman.android.utils.startNewActivityAnimation
import me.kzaman.android.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity() {
    private lateinit var binding: ActivityProductBinding

    lateinit var rlToolbar: RelativeLayout
    private lateinit var tvTitle: TextView
    private lateinit var ivBackButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        rlToolbar = findViewById(R.id.toolbar_root)
        tvTitle = findViewById(R.id.tv_toolbar_title)
        ivBackButton = findViewById(R.id.iv_back_button)

        ivBackButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun init() {}

    override fun setToolbarTitle(title: String) {
        setToolbarTitle(title, tvTitle)
    }

    override fun hideToolbar() {
        rlToolbar.visibility = View.GONE
    }

    override fun showToolbar(isBackButton: Boolean) {
        ivBackButton.visible(isBackButton)
        rlToolbar.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            startNewActivityAnimation(DashboardActivity::class.java, false);
        }
    }
}