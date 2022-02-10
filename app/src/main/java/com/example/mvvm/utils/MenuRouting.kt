package com.example.mvvm.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.mvvm.ui.view.activities.OrdersActivity
import com.example.mvvm.ui.view.activities.ProductActivity
import com.example.mvvm.ui.view.activities.SettingsActivity
import com.example.mvvm.utils.Constants.Companion.ADVISER_LIST
import com.example.mvvm.utils.Constants.Companion.CUSTOMER_LIST
import com.example.mvvm.utils.Constants.Companion.DAILY_CALL_PLAN
import com.example.mvvm.utils.Constants.Companion.DCR
import com.example.mvvm.utils.Constants.Companion.DELIVERY
import com.example.mvvm.utils.Constants.Companion.HISTORY
import com.example.mvvm.utils.Constants.Companion.MONTHLY_TOUR_PLAN
import com.example.mvvm.utils.Constants.Companion.ORDER
import com.example.mvvm.utils.Constants.Companion.ORDER_APPROVAL
import com.example.mvvm.utils.Constants.Companion.ORDER_HISTORY
import com.example.mvvm.utils.Constants.Companion.PAYMENT_COLLECTION
import com.example.mvvm.utils.Constants.Companion.PRODUCT_LIST
import com.example.mvvm.utils.Constants.Companion.RETURN
import com.example.mvvm.utils.Constants.Companion.REVIEW_MTP
import com.example.mvvm.utils.Constants.Companion.REVIEW_ORDER
import com.example.mvvm.utils.Constants.Companion.REVIEW_REQUEST
import com.example.mvvm.utils.Constants.Companion.SETTING
import com.example.mvvm.utils.Constants.Companion.TRACKING

/*
*
* dashboard menu routing
* activity, featureId
*/

fun menuRouting(activity: Activity, featureId: String) {
    when (featureId) {
        MONTHLY_TOUR_PLAN -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        DAILY_CALL_PLAN -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        DCR -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        ORDER -> {
            goToNextActivityAnimation(activity, Intent(activity, OrdersActivity::class.java))
        }
        ORDER_HISTORY -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        CUSTOMER_LIST -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        ADVISER_LIST -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        RETURN -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        SETTING -> {
            goToNextActivityAnimation(activity, Intent(activity, SettingsActivity::class.java))
        }
        DELIVERY -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        PAYMENT_COLLECTION -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        PRODUCT_LIST -> {
            goToNextActivityAnimation(activity, Intent(activity, ProductActivity::class.java))
        }
        HISTORY -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        REVIEW_MTP -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        REVIEW_ORDER -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        ORDER_APPROVAL -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        REVIEW_REQUEST -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        TRACKING -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        else -> {
            Toast.makeText(activity, "Unknown Feature", Toast.LENGTH_SHORT).show()
        }
    }
}