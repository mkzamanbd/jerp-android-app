package me.kzaman.android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.kzaman.android.database.entities.CartItemsEntities
import me.kzaman.android.database.entities.ProductEntities


@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ArrayList<ProductEntities>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProducts(cartItemsEntities: CartItemsEntities)

    @Query("SELECT * FROM products")
    suspend fun getLocalProducts(): List<ProductEntities>

    @Query("SELECT * FROM carts WHERE customer_id = :customerId LIMIT 1")
    suspend fun getCartItems(customerId: String): CartItemsEntities

    @Query("DELETE FROM products")
    suspend fun deleteProductTable()
}