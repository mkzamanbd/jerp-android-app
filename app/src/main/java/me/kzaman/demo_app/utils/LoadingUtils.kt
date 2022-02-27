package me.kzaman.demo_app.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import me.kzaman.demo_app.R
import me.kzaman.demo_app.interfaces.LoadingConfig

class LoadingUtils(
    private val mContext: Context,
) : LoadingConfig {

    init {
        onCreateLoadingDialog()
    }

    private lateinit var loadingDialog: Dialog
    private fun onCreateLoadingDialog() {
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