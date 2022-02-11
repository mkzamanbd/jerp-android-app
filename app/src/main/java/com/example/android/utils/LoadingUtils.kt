package com.example.android.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import com.example.android.R
import com.example.android.interfaces.LoadingConfig

class LoadingUtils(
    val context: Context,
) : LoadingConfig {

    init {
        createLoadingDialog()
    }

    private lateinit var loadingDialog: Dialog

    private fun createLoadingDialog(): Dialog {
        loadingDialog = Dialog(context)
        loadingDialog.setContentView(R.layout.dialog_loading_layout)

        if (loadingDialog.window != null) {
            loadingDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        loadingDialog.setCancelable(false)

        return loadingDialog
    }

    override fun showLoadingDialog() = loadingDialog.show()

    /**
     *
     * @ Dismiss progress dialog
     */
    override fun dismissLoadingDialog() = loadingDialog.hide()

    fun isLoading(isLoading: Boolean) {
        if (isLoading && !loadingDialog.isShowing) {
            showLoadingDialog()
        } else {
            dismissLoadingDialog()
        }
    }
}