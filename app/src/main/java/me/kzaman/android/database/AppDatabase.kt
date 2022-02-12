package me.kzaman.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kzaman.android.database.dao.ProductDao
import me.kzaman.android.database.entities.ProductEntities


@Database(
    entities = [ProductEntities::class],
    version = 7
)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}