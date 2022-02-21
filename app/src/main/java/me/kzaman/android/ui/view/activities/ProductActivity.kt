package me.kzaman.android.ui.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import me.kzaman.android.R
import me.kzaman.android.base.BaseActivity
import me.kzaman.android.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity() {

    private lateinit var rlToolbar: RelativeLayout
    private lateinit var tvTitle: TextView
    private lateinit var ivBackButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragments)
        init()

    }

    override fun init() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_view) as NavHostFragment
        val navController = navHost.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        navGraph.setStartDestination(R.id.productListFragment)
        navController.graph = navGraph

        rlToolbar = findViewById(R.id.toolbar_root)
        tvTitle = findViewById(R.id.tv_toolbar_title)
        ivBackButton = findViewById(R.id.iv_back_button)

        ivBackButton.setOnClickListener {
            onBackPressed()
        }
    }

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
            super.onBackPressed()
            overridePendingTransition(0, R.anim.animation_slide_out_right)
        }
    }
}