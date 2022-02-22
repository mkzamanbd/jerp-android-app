package me.kzaman.android.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
class CartItemsEntities(
    @PrimaryKey @NonNull @ColumnInfo(name = "customer_id") val customerId: String,
    @ColumnInfo(name = "product_detail") var productDetail: String,
)