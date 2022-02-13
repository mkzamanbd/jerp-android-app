package me.kzaman.android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.kzaman.android.database.entities.HomeParentMenuEntities
import me.kzaman.android.database.entities.ProductEntities


@Dao
interface ParentMenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParentMenu(parentMenu: ArrayList<HomeParentMenuEntities>): LongArray

    @Query("SELECT * FROM home_parent_menu_entities")
    suspend fun getParentMenu(): List<HomeParentMenuEntities>

    @Query("SELECT * FROM home_parent_menu_entities WHERE parent_menu_title = 'BOTTOM'")
    suspend fun getLocalBottomMenu(): List<HomeParentMenuEntities>
}