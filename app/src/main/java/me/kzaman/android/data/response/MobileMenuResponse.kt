package me.kzaman.android.data.response

import com.google.gson.annotations.SerializedName

data class MobileMenuResponse(
    val code: Int,
    val data: Data,
    val message: Any,
    val status: String,
)

data class Data(
    @SerializedName("bottom") val bottomParentMenu: List<UserParentMenuModel>? = ArrayList(),
    @SerializedName("menu") val topParentMenu: List<UserParentMenuModel> ? = ArrayList(),
)

data class UserParentMenuModel(
    @SerializedName("id") val menuId: String,
    @SerializedName("menu_name") val menuName: String,
    @SerializedName("menu") val menuItems: List<UserChildMenuModel>,
    @SerializedName("nav_type") val navType: String,
)

data class UserChildMenuModel(
    @SerializedName("id") val menuId: String,
    @SerializedName("feature_id") val featureId: String,
    @SerializedName("menu_name") val menuName: String,
    @SerializedName("nav_type") val navType: String,
    var iconId: Int,
)