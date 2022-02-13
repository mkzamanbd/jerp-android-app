package me.kzaman.android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.kzaman.android.database.entities.HomeChildMenuEntities

@Dao
interface ChildMenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildMenu(menu: ArrayList<HomeChildMenuEntities>): LongArray

    @Query("SELECT * FROM home_child_menu_entities")
    suspend fun getChildMenu(): List<HomeChildMenuEntities>

}