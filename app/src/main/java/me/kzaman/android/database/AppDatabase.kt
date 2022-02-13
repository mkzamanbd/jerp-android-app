package me.kzaman.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kzaman.android.database.dao.ChildMenuDao
import me.kzaman.android.database.dao.ParentMenuDao
import me.kzaman.android.database.dao.ProductDao
import me.kzaman.android.database.entities.HomeChildMenuEntities
import me.kzaman.android.database.entities.HomeParentMenuEntities
import me.kzaman.android.database.entities.ProductEntities


@Database(
    entities = [
        ProductEntities::class,
        HomeParentMenuEntities::class,
        HomeChildMenuEntities::class,
    ],
    version = 12
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun parentMenuDao(): ParentMenuDao
    abstract fun childMenuDao(): ChildMenuDao
}