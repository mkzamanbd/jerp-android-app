package com.example.mvvm.ui.view.activities

import android.os.Bundle
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.databinding.ActivityOrdersBinding
import com.example.mvvm.utils.startNewActivityAnimation

class OrdersActivity : BaseActivity() {
    private lateinit var binding: ActivityOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

    override fun init() {}
    override fun setToolbarTitle(title: String) {}

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            startNewActivityAnimation(DashboardActivity::class.java, false);
        }
    }
}