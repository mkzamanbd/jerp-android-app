package me.kzaman.demo_app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import me.kzaman.demo_app.R
import me.kzaman.demo_app.ui.activities.CustomerActivity
import me.kzaman.demo_app.ui.activities.OrdersActivity
import me.kzaman.demo_app.ui.activities.ProductActivity
import me.kzaman.demo_app.ui.activities.SettingsActivity
import me.kzaman.demo_app.utils.Constants.Companion.ADVISER_LIST
import me.kzaman.demo_app.utils.Constants.Companion.CUSTOMER_LIST
import me.kzaman.demo_app.utils.Constants.Companion.DAILY_CALL_PLAN
import me.kzaman.demo_app.utils.Constants.Companion.DCR
import me.kzaman.demo_app.utils.Constants.Companion.DELIVERY
import me.kzaman.demo_app.utils.Constants.Companion.DEPOSIT
import me.kzaman.demo_app.utils.Constants.Companion.HISTORY
import me.kzaman.demo_app.utils.Constants.Companion.MONTHLY_TOUR_PLAN
import me.kzaman.demo_app.utils.Constants.Companion.OFFER
import me.kzaman.demo_app.utils.Constants.Companion.ORDER
import me.kzaman.demo_app.utils.Constants.Companion.ORDER_APPROVAL
import me.kzaman.demo_app.utils.Constants.Companion.ORDER_HISTORY
import me.kzaman.demo_app.utils.Constants.Companion.PAYMENT_COLLECTION
import me.kzaman.demo_app.utils.Constants.Companion.PRODUCT_LIST
import me.kzaman.demo_app.utils.Constants.Companion.RETURN
import me.kzaman.demo_app.utils.Constants.Companion.REVIEW_MTP
import me.kzaman.demo_app.utils.Constants.Companion.REVIEW_ORDER
import me.kzaman.demo_app.utils.Constants.Companion.REVIEW_REQUEST
import me.kzaman.demo_app.utils.Constants.Companion.SETTING
import me.kzaman.demo_app.utils.Constants.Companion.TA_DA
import me.kzaman.demo_app.utils.Constants.Companion.TRACKING


/*
*
* dashboard menu routing
* mActivity, featureId
*/

fun menuRouting(mContext: Context, featureId: String) {
    val mActivity = (mContext as Activity)
    when (featureId) {
        MONTHLY_TOUR_PLAN -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        DAILY_CALL_PLAN -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        DCR -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        ORDER -> {
            goToNextActivityAnimation(
                mActivity,
                Intent(mActivity, OrdersActivity::class.java),
                true,
                featureId
            )
        }
        ORDER_HISTORY -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        CUSTOMER_LIST -> {
            goToNextActivityAnimation(
                mActivity,
                Intent(mActivity, CustomerActivity::class.java),
                true,
                featureId
            )
        }
        ADVISER_LIST -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        RETURN -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        SETTING -> {
            goToNextActivityAnimation(
                mActivity,
                Intent(mActivity, SettingsActivity::class.java),
                true,
                featureId
            )
        }
        DELIVERY -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        PAYMENT_COLLECTION -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        PRODUCT_LIST -> {
            goToNextActivityAnimation(
                mActivity,
                Intent(mActivity, ProductActivity::class.java),
                true,
                featureId
            )
        }
        HISTORY -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        REVIEW_MTP -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        REVIEW_ORDER -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        ORDER_APPROVAL -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        REVIEW_REQUEST -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        TRACKING -> {
            Toast.makeText(mActivity, featureId, Toast.LENGTH_SHORT).show()
        }
        else -> {
            Toast.makeText(mActivity, "Unknown", Toast.LENGTH_SHORT).show()
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