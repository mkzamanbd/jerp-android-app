package com.example.mvvm.utils

import android.content.Context
import com.example.mvvm.interfaces.LoadingConfig

open class LoadingUtils(context: Context) : LoadingConfig {

    /**
     * @ Display progress dialog
     */
    override fun showProgressDialog() {}

    /**
     *
     * @ Dismiss progress dialog
     */
    override fun dismissProgressDialog() {}
}