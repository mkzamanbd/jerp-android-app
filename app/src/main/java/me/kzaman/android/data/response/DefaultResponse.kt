package me.kzaman.android.data.response

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("response_code") val responseCode: Int?,
    val success: Boolean?,
)
