package me.kzaman.android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.kzaman.android.database.entities.CustomerEntities


@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(customers: ArrayList<CustomerEntities>): LongArray

    @Query("SELECT * FROM customers")
    suspend fun getLocalCustomers(): List<CustomerEntities>

    @Query("DELETE FROM customers")
    suspend fun deleteAllCustomers()
}