package me.kzaman.android.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_menus")
class SubMenuEntities(
    @PrimaryKey
    @ColumnInfo(name = "menu_id")
    @NonNull
    val menuId: String,

    @ColumnInfo(name = "parent_menu_id")
    val parentMenuId: String,

    @ColumnInfo(name = "feature_id")
    val featureId: String,

    @ColumnInfo(name = "menu_title")
    val menuTitle: String,

    @ColumnInfo(name = "menu_type")
    val menuType: String,

    @ColumnInfo(name = "icon_id")
    val iconId: Int,
)