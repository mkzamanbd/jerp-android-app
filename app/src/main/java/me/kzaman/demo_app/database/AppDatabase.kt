package me.kzaman.demo_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kzaman.demo_app.database.dao.CustomerDao
import me.kzaman.demo_app.database.dao.MenuDao
import me.kzaman.demo_app.database.dao.OrderDao
import me.kzaman.demo_app.database.entities.CustomerEntities
import me.kzaman.demo_app.database.entities.SubMenuEntities
import me.kzaman.demo_app.database.entities.MenuEntities
import me.kzaman.demo_app.database.entities.CartItemsEntities
import me.kzaman.demo_app.database.entities.ProductEntities


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