package com.example.mvvm.utils

import android.content.Context
import android.widget.Toast
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
* context, featureId
*/

fun menuRouting(context: Context, featureId: String) {
    when (featureId) {
        MONTHLY_TOUR_PLAN -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        DAILY_CALL_PLAN -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        DCR -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        ORDER -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        ORDER_HISTORY -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        CUSTOMER_LIST -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        ADVISER_LIST -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        RETURN -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        SETTING -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        DELIVERY -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        PAYMENT_COLLECTION -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        PRODUCT_LIST -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        HISTORY -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        REVIEW_MTP -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        REVIEW_ORDER -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        ORDER_APPROVAL -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        REVIEW_REQUEST -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        TRACKING -> {
            Toast.makeText(context, featureId, Toast.LENGTH_SHORT).show()
        }
        else -> {
            Toast.makeText(context, "Unknown Feature", Toast.LENGTH_SHORT).show()
        }
    }


}