package me.kzaman.android.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.kzaman.android.R
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.view.activities.DashboardActivity
import me.kzaman.android.ui.view.fragments.auth.LoginFragment
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

fun Fragment.handleFragmentApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null,
) {
    when {
        failure.isNetworkError -> {
            requireView().snackBar("Please check your internet connection", retry)
        }
        failure.statusCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackBar("You've entered incorrect email or password")
            } else {
                (this as BaseFragment<*>).logout()
            }
        }
        failure.statusCode == 404 -> {
            requireView().snackBar("Url not found!")
        }
        failure.statusCode == 422 -> {
            requireView().snackBar("The given data was invalid")
        }
        failure.statusCode == 500 -> {
            requireView().snackBar("Internal server error")
        }
        else -> {
            val error = "Code: ${failure.statusCode},  ${failure.errorMessage}"
            requireView().snackBar(error)
        }
    }
}

fun Activity.handleActivityApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null,
) {
    when {
        failure.isNetworkError -> {
            toastError("Please check your internet connection")
        }
        failure.statusCode == 401 -> {
            (this as DashboardActivity).performLogout()
        }
        failure.statusCode == 404 -> {
            toastError("Url not found!")
        }
        failure.statusCode == 422 -> {
            toastError("The given data was invalid")
        }
        failure.statusCode == 500 -> {
            toastError("Internal server error")
        }
        else -> {
            val error = "Code: ${failure.statusCode},  ${failure.errorMessage}"
            toastError(error)
        }
    }
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val option = RequestOptions().placeholder(progressDrawable).error(R.mipmap.ic_launcher_round)
    Glide.with(this.context).setDefaultRequestOptions(option).load(uri).into(this)
}

fun hideSoftKeyboard(context: Context, mEtSearch: EditText) {
    mEtSearch.clearFocus()
    val inputMethod = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
    inputMethod.hideSoftInputFromWindow(mEtSearch.windowToken, 0)
}

/**
 * ...start next fragment
 * ...passing value one fragment to next fragment
 * ...execute actionId wise
 */
fun goToNextFragment(actionId: Int, mView: View, bundle: Bundle?) {
    bundle?.let {
        Navigation.findNavController(mView).navigate(actionId, bundle)
    } ?: run {
        Navigation.findNavController(mView).navigate(actionId)
    }
}

/*
* horizontalColumnRecyclerView
* context, recyclerView, column
*/
fun horizontalColumnRecyclerView(
    activity: Activity?,
    recyclerView: RecyclerView,
    column: Int,
): RecyclerView {
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = GridLayoutManager(activity, column)
    recyclerView.isNestedScrollingEnabled = false
    return recyclerView
}

/*
*
* activity start animation
*
*/

fun goToNextActivityAnimation(activity: Activity, intent: Intent, isNextActivity: Boolean = true) {
    activity.startActivity(intent)
    if (isNextActivity) {
        activity.overridePendingTransition(R.anim.animation_slide_in_right,
            R.anim.animation_slide_out_left)
    } else {
        activity.overridePendingTransition(R.anim.animation_slide_in_left,
            R.anim.animation_slide_out_right)
    }

}

fun <A : Activity> Activity.startNewActivityAnimation(
    activity: Class<A>,
    isNextActivity: Boolean = true,
) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
    if (isNextActivity) {
        this.overridePendingTransition(R.anim.animation_slide_in_right,
            R.anim.animation_slide_out_left)
    } else {
        this.overridePendingTransition(R.anim.animation_slide_in_left,
            R.anim.animation_slide_out_right)
    }
}

fun startAlphaAnimation(view: View, duration: Long, visibility: Int) {
    val alphaAnimation =
        if (visibility == View.VISIBLE) AlphaAnimation(0f, 1f) else AlphaAnimation(1f, 0f)
    view.visibility = visibility
    alphaAnimation.duration = duration
    alphaAnimation.fillAfter = true
    view.startAnimation(alphaAnimation)
}