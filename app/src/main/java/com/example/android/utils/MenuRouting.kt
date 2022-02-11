package com.example.android.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.android.R
import com.example.android.ui.view.activities.OrdersActivity
import com.example.android.ui.view.activities.ProductActivity
import com.example.android.ui.view.activities.SettingsActivity
import com.example.android.utils.Constants.Companion.ADVISER_LIST
import com.example.android.utils.Constants.Companion.CUSTOMER_LIST
import com.example.android.utils.Constants.Companion.DAILY_CALL_PLAN
import com.example.android.utils.Constants.Companion.DCR
import com.example.android.utils.Constants.Companion.DELIVERY
import com.example.android.utils.Constants.Companion.DEPOSIT
import com.example.android.utils.Constants.Companion.HISTORY
import com.example.android.utils.Constants.Companion.MONTHLY_TOUR_PLAN
import com.example.android.utils.Constants.Companion.OFFER
import com.example.android.utils.Constants.Companion.ORDER
import com.example.android.utils.Constants.Companion.ORDER_APPROVAL
import com.example.android.utils.Constants.Companion.ORDER_HISTORY
import com.example.android.utils.Constants.Companion.PAYMENT_COLLECTION
import com.example.android.utils.Constants.Companion.PRODUCT_LIST
import com.example.android.utils.Constants.Companion.RETURN
import com.example.android.utils.Constants.Companion.REVIEW_MTP
import com.example.android.utils.Constants.Companion.REVIEW_ORDER
import com.example.android.utils.Constants.Companion.REVIEW_REQUEST
import com.example.android.utils.Constants.Companion.SETTING
import com.example.android.utils.Constants.Companion.TA_DA
import com.example.android.utils.Constants.Companion.TRACKING




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

/*
*
* dashboard menu icon
* @params feature id
*/

fun getMenuIcon(featureId: String): Int {
    when (featureId) {
        ORDER -> {
            return R.drawable.ic_bt_order_unselect
        }
        DEPOSIT -> {
            return R.drawable.ic_bt_deposit_unselect
        }
        TA_DA -> {
            return R.drawable.ic_bt_ta_da_unselect
        }
        Constants.NOTICE -> {
            return R.drawable.ic_bt_warning_unselect
        }
        DCR -> {
            return R.drawable.ic_bt_visit_un_select
        }
        TRACKING -> {
            return R.drawable.ic_bt_tracking_unselect
        }
        RETURN -> {
            return R.drawable.ic_bt_return
        }
        OFFER -> {
            return R.drawable.ic_bt_offers
        }
        PAYMENT_COLLECTION -> {
            return R.drawable.ic_bt_payments
        }
        PRODUCT_LIST -> {
            return R.drawable.ic_bt_product
        }
        CUSTOMER_LIST -> {
            return R.drawable.ic_bt_customer
        }
        REVIEW_ORDER -> {
            return R.drawable.ic_hm_review_order
        }
        REVIEW_REQUEST -> {
            return R.drawable.ic_hm_review
        }
        REVIEW_MTP -> {
            return R.drawable.ic_hm_review_mtp
        }
    }
    return R.drawable.ic_no_image_hm
}