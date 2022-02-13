package me.kzaman.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kzaman.android.database.dao.MenuDao
import me.kzaman.android.database.dao.ProductDao
import me.kzaman.android.database.entities.SubMenuEntities
import me.kzaman.android.database.entities.MenuEntities
import me.kzaman.android.database.entities.ProductEntities


@Database(
    entities = [
        MenuEntities::class,
        SubMenuEntities::class,
        ProductEntities::class,
    ], version = 14
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
    abstract fun productDao(): ProductDao
}