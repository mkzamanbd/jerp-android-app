package me.kzaman.demo_app.database.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import me.kzaman.demo_app.database.entities.CustomerCartEntities
import me.kzaman.demo_app.database.entities.CustomerEntities


@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(customers: ArrayList<CustomerEntities>): LongArray

    @Query("SELECT * FROM customers")
    suspend fun getLocalCustomers(): List<CustomerEntities>

    @RawQuery
    suspend fun getLocalCustomers(query: SupportSQLiteQuery): List<CustomerCartEntities>

    @Query("DELETE FROM customers")
    suspend fun deleteAllCustomers()
}