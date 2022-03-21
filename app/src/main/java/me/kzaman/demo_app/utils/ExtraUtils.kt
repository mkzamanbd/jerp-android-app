package me.kzaman.demo_app.utils

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.GsonBuilder
import me.kzaman.demo_app.data.model.CartItemsModel
import me.kzaman.demo_app.data.model.ProductInfo
import org.json.JSONArray
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar
import kotlin.collections.ArrayList


/**
 * ...add comma for amount
 * @param number decimal amount
 * @return amount with comma
 */
fun numberFormat(number: Double): String {
    val formatter = DecimalFormat(amountFormat(doubleValueFormat(number)))
    return formatter.format(number)
}

/**
 * ...get amount format
 * @param amount actual amount
 * @return amount format
 */
fun amountFormat(amount: String): String {
    return when (amount.length) {
        5 -> "##,###.##"
        6 -> "#,##,###.##"
        7 -> "##,##,###.##"
        8 -> "#,##,##,###.##"
        9 -> "##,##,##,###.##"
        10 -> "#,##,##,##,###.##"
        11 -> "##,##,##,##,###.##"
        4 -> "#,###.##"
        else -> "#,###.##"
    }
}

/**
 * get double format value with two decimal places
 * when decimal number is 0 then return int format
 * @param d double value
 * @return double format value
 */
fun doubleValueFormat(d: Double): String {
    return if (d.equals(d.toLong())) {
        String.format("%d", d.toLong())
    } else {
        String.format("%.2f", d)
    }
}

fun countJsonObject(json: String): Int {
    return try {
        val jsonArray = JSONArray(json)
        jsonArray.length()
    } catch (ex: Exception) {
        0
    }
}

fun getProductFromCartJson(json: String, products: List<ProductInfo>): List<ProductInfo> {
    val gson = GsonBuilder().create()
    val jsonArray = gson.fromJson(json, Array<CartItemsModel>::class.java).toList()
    val selectedProduct = ArrayList<ProductInfo>()

    for (item in jsonArray) {
        products.forEach { product ->
            if (item.productId == product.productId) {
                Log.d("jsonProdId", "id is: ${product.productId}")
                product.quantity = item.quantity
                product.isProductSelected = true
                selectedProduct.add(product)
                return@forEach
            }
        }
    }
    return selectedProduct
}

fun todayDate(): String {
    var date = Date()
    val calendar = Calendar.getInstance()
    calendar.time = date
    date = calendar.time
    return dateFormatDDMMYYYYFormDate(date)
}

@SuppressLint("SimpleDateFormat")
fun dateFormatDDMMYYYYFormDate(value: Date): String {
    val outPutPattern = "dd-MM-yyyy"
    val dateFormat = SimpleDateFormat(outPutPattern)
    lateinit var result: String
    try {
        result = dateFormat.format(value)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return result
}