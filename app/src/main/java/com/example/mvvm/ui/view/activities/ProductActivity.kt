package com.example.mvvm.ui.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mvvm.R
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.databinding.ActivityProductBinding
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity() {
    private lateinit var binding: ActivityProductBinding

    lateinit var rlToolbar: RelativeLayout
    lateinit var tvTitle: TextView
    lateinit var tvBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        rlToolbar = findViewById(R.id.toolbar_root)
        tvTitle = findViewById(R.id.tv_toolbar_title)
        tvBack = findViewById(R.id.iv_back_button)
    }

    override fun init() {}

    override fun setToolbarTitle(title: String) {
        setToolbarTitle(title, tvTitle)
    }

    override fun hideToolbar() {
        rlToolbar.visibility = View.GONE
    }

    override fun showToolbar(isBackButton: Boolean) {
        tvBack.visible(isBackButton)
        rlToolbar.visibility = View.VISIBLE
    }
}