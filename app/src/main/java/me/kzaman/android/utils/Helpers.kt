package me.kzaman.android.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import me.kzaman.android.R
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.view.activities.AuthActivity
import me.kzaman.android.ui.view.activities.DashboardActivity


fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

@SuppressLint("InflateParams")
fun View.showSnackBar(mActivity: Activity, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
    val layout = snackBar.view as Snackbar.SnackbarLayout
    val inflater = LayoutInflater.from(mActivity)
    val snackView = inflater.inflate(R.layout.layout_snackbar, null)
    layout.setPadding(0, 0, 0, 0)
    layout.addView(snackView)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()
}

fun handleNetworkError(
    failure: Resource.Failure,
    mActivity: Activity,
    retry: (() -> Unit)? = null,
) {
    when {
        failure.isNetworkError -> {
            val view: View = mActivity.window.decorView.rootView
            view.showSnackBar(mActivity, retry)
        }
        failure.statusCode == 401 -> {
            if (mActivity is AuthActivity) {
                mActivity.toastError("You've entered incorrect email or password")
            } else {
                mActivity.toastError("Unauthenticated")
                (mActivity as? DashboardActivity)?.performLogout()
            }
        }
        failure.statusCode == 404 -> {
            mActivity.toastError("Url not found!")
        }
        failure.statusCode == 422 -> {
            mActivity.toastWarning("The given data was invalid")
        }
        failure.statusCode == 500 -> {
            mActivity.toastError("Internal server error")
        }
        else -> {
            val error = "Code: ${failure.statusCode},  ${failure.errorMessage}"
            mActivity.toastError(error)
        }
    }
}

fun ImageView.loadImage(uri: String) {
    val option =
        RequestOptions().placeholder(R.drawable.img_avatar).error(R.drawable.img_avatar)
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

fun goToNextActivityAnimation(
    activity: Activity,
    intent: Intent,
    isNextActivity: Boolean = true,
    featureId: String? = null,
) {
    intent.putExtra("featureId", featureId)
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

fun getTintedDrawable(inputDrawable: Drawable, @ColorInt color: Int): Drawable {
    val wrapDrawable = DrawableCompat.wrap(inputDrawable)
    DrawableCompat.setTint(wrapDrawable, color)
    DrawableCompat.setTintMode(wrapDrawable, PorterDuff.Mode.SRC_IN)
    return wrapDrawable
}