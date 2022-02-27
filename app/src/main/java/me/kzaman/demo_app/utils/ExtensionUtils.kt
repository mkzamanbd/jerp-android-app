package me.kzaman.demo_app.utils

import me.kzaman.demo_app.data.model.ProductInfo

fun List<ProductInfo>.isProductAlreadyExist(productId: String): Boolean {
    for (product in this) {
        if (product.productId == productId) {
            return true
        }
    }
    return false
}