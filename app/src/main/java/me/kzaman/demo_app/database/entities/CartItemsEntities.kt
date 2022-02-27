package me.kzaman.demo_app.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
class CartItemsEntities(
    @PrimaryKey @NonNull @ColumnInfo(name = "customer_id") val customerId: String,
    @ColumnInfo(name = "cart_json") var cartJson: String,
)