package me.kzaman.android.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import me.kzaman.android.R
import me.kzaman.android.interfaces.LoadingConfig

class LoadingUtils(
    val mContext: Context,
) : LoadingConfig {

    init {
        createLoadingDialog()
    }

    lateinit var loadingDialog: Dialog
    private fun createLoadingDialog() {
        loadingDialog = Dialog(mContext)
        loadingDialog.setContentView(R.layout.dialog_loading_layout)

        if (loadingDialog.window != null) {
            loadingDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        loadingDialog.setCancelable(false)
    }

    override fun showLoadingDialog() {
        try {
            loadingDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *
     * @ Dismiss progress dialog
     */
    override fun dismissLoadingDialog() {
        try {
            loadingDialog.hide()
            loadingDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            showLoadingDialog()
        } else {
            dismissLoadingDialog()
        }
    }
}