package me.kzaman.android.utils

import me.kzaman.android.data.model.ProductInfo

fun List<ProductInfo>.isProductAlreadyExist(productId: String): Boolean {
    for (product in this) {
        if (product.productId == productId) {
            return true
        }
    }
    return false
}