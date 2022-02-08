package com.example.mvvm.base

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm.interfaces.InitialComponent

abstract class BaseActivity : AppCompatActivity(), InitialComponent {
    abstract override fun init()

    abstract override fun setToolbarTitle(title: String)

    /**
     * ...set toolbar title
     * ...modify title and textView if needed
     * @param title toolbar title
     * @param tvTitle textView object
     */
    fun setToolbarTitle(title: String, tvTitle: TextView) {
        tvTitle.text = title
    }

    open fun hideToolbar() {}
    open fun showToolbar() {}
}