package com.example.android.base

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.interfaces.InitialComponent
import com.example.android.utils.LoadingUtils

abstract class BaseActivity : AppCompatActivity(), InitialComponent {

    protected lateinit var loadingUtils: LoadingUtils

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
    open fun showToolbar(isBackButton: Boolean = false) {}
}