package com.example.mvvm.ui.view.activities

import android.os.Bundle
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.databinding.ActivityProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity() {
    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun init() {}

    override fun setToolbarTitle(title: String) {
        setToolbarTitle(title, binding.toolbar.tvToolbarTitle)
    }
}