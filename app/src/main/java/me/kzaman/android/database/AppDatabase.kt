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
        ProductEntities::class,
        MenuEntities::class,
        SubMenuEntities::class,
    ],
    version = 13
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun menuDao(): MenuDao
}