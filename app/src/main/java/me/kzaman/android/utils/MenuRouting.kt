package me.kzaman.android.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import me.kzaman.android.R
import me.kzaman.android.ui.view.activities.CustomerActivity
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.activities.ProductActivity
import me.kzaman.android.ui.view.activities.SettingsActivity
import me.kzaman.android.utils.Constants.Companion.ADVISER_LIST
import me.kzaman.android.utils.Constants.Companion.CUSTOMER_LIST
import me.kzaman.android.utils.Constants.Companion.DAILY_CALL_PLAN
import me.kzaman.android.utils.Constants.Companion.DCR
import me.kzaman.android.utils.Constants.Companion.DELIVERY
import me.kzaman.android.utils.Constants.Companion.DEPOSIT
import me.kzaman.android.utils.Constants.Companion.HISTORY
import me.kzaman.android.utils.Constants.Companion.MONTHLY_TOUR_PLAN
import me.kzaman.android.utils.Constants.Companion.OFFER
import me.kzaman.android.utils.Constants.Companion.ORDER
import me.kzaman.android.utils.Constants.Companion.ORDER_APPROVAL
import me.kzaman.android.utils.Constants.Companion.ORDER_HISTORY
import me.kzaman.android.utils.Constants.Companion.PAYMENT_COLLECTION
import me.kzaman.android.utils.Constants.Companion.PRODUCT_LIST
import me.kzaman.android.utils.Constants.Companion.RETURN
import me.kzaman.android.utils.Constants.Companion.REVIEW_MTP
import me.kzaman.android.utils.Constants.Companion.REVIEW_ORDER
import me.kzaman.android.utils.Constants.Companion.REVIEW_REQUEST
import me.kzaman.android.utils.Constants.Companion.SETTING
import me.kzaman.android.utils.Constants.Companion.TA_DA
import me.kzaman.android.utils.Constants.Companion.TRACKING


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
            goToNextActivityAnimation(
                activity,
                Intent(activity, OrdersActivity::class.java),
                true,
                featureId
            )
        }
        ORDER_HISTORY -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        CUSTOMER_LIST -> {
            goToNextActivityAnimation(
                activity,
                Intent(activity, CustomerActivity::class.java),
                true,
                featureId
            )
        }
        ADVISER_LIST -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        RETURN -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        SETTING -> {
            goToNextActivityAnimation(
                activity,
                Intent(activity, SettingsActivity::class.java),
                true,
                featureId
            )
        }
        DELIVERY -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        PAYMENT_COLLECTION -> {
            Toast.makeText(activity, featureId, Toast.LENGTH_SHORT).show()
        }
        PRODUCT_LIST -> {
            goToNextActivityAnimation(
                activity,
                Intent(activity, ProductActivity::class.java),
                true,
                featureId
            )
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
            Toast.makeText(activity, "Unknown", Toast.LENGTH_SHORT).show()
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
            return R.drawable.ic_baseline_card_giftcard_24
        }
        PAYMENT_COLLECTION -> {
            return R.drawable.ic_bt_payments
        }
        PRODUCT_LIST -> {
            return R.drawable.ic_bt_product
        }
        CUSTOMER_LIST -> {
            return R.drawable.ic_baseline_people_24
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
    return R.drawable.ic_baseline_filter_none_24
}