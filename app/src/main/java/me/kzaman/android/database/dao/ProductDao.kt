package me.kzaman.android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.kzaman.android.database.entities.ProductEntities


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ArrayList<ProductEntities>): LongArray

    @Query("SELECT * FROM products_list")
    suspend fun getLocalProducts(): List<ProductEntities>
}