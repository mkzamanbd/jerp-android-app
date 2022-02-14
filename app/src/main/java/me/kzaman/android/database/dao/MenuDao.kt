package me.kzaman.android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.kzaman.android.database.entities.MenuEntities
import me.kzaman.android.database.entities.SubMenuEntities


@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParentMenu(parentMenu: ArrayList<MenuEntities>): LongArray

    @Query("SELECT * FROM menus")
    suspend fun getParentMenu(): List<MenuEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubMenu(menus: ArrayList<SubMenuEntities>): LongArray

    @Query("SELECT * FROM sub_menus")
    suspend fun getSubMenu(): List<SubMenuEntities>

    @Query("DELETE FROM menus")
    suspend fun deleteMenuTable()

    @Query("DELETE FROM sub_menus")
    suspend fun deleteSubMenuTable()
}