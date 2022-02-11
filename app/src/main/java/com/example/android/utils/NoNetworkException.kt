package com.example.android.utils

import java.io.IOException

class NoNetworkException internal constructor() : IOException() {
    val errorCode: Int get() = 559
    val errorMessage: String get() = "Please check internet connection"
}