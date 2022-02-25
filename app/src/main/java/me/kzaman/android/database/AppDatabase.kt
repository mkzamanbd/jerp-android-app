package me.kzaman.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kzaman.android.database.dao.CustomerDao
import me.kzaman.android.database.dao.MenuDao
import me.kzaman.android.database.dao.OrderDao
import me.kzaman.android.database.entities.CustomerEntities
import me.kzaman.android.database.entities.SubMenuEntities
import me.kzaman.android.database.entities.MenuEntities
import me.kzaman.android.database.entities.CartItemsEntities
import me.kzaman.android.database.entities.ProductEntities


@Database(
    entities = [
        MenuEntities::class,
        SubMenuEntities::class,
        ProductEntities::class,
        CustomerEntities::class,
        CartItemsEntities::class,
    ], version = 30
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
    abstract fun orderDao(): OrderDao
    abstract fun customerDao(): CustomerDao
}