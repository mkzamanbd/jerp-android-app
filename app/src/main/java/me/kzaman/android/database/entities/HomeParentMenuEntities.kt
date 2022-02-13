package me.kzaman.android.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_parent_menu_entities")
data class HomeParentMenuEntities(
    @PrimaryKey
    @ColumnInfo(name = "parent_menu_id")
    @NonNull
    val parentMenuId: String,

    @ColumnInfo(name = "parent_menu_title")
    val parentMenuTitle: String,

    @ColumnInfo(name = "nav_type")
    val navType: String,
)