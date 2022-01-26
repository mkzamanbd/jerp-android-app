package com.example.simplelogin.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.simplelogin.base.BaseFragment
import com.example.simplelogin.network.Resource
import com.example.simplelogin.ui.view.DashboardActivity
import com.example.simplelogin.ui.view.auth.LoginFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}


fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()
}

fun Fragment.logout() = lifecycleScope.launch {
    if (activity is DashboardActivity) {
        (activity as DashboardActivity).performLogout()
    }
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackBar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackBar("You've entered incorrect email or password")
            }
            else{
                (this as BaseFragment<*>).logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackBar(error)
        }
    }
}