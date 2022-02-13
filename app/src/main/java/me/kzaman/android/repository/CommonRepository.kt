package me.kzaman.android.repository

import me.kzaman.android.base.BaseRepository
import me.kzaman.android.database.dao.ChildMenuDao
import me.kzaman.android.database.dao.ParentMenuDao
import me.kzaman.android.database.entities.HomeChildMenuEntities
import me.kzaman.android.database.entities.HomeParentMenuEntities
import me.kzaman.android.network.api.CommonApi
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val api: CommonApi,
    private val parentMenuDao: ParentMenuDao,
    private val childMenuDao: ChildMenuDao,
) : BaseRepository(api) {

    suspend fun getMobileMenu() = safeApiCall { api.getMobileMenu() }

    suspend fun saveParentMenuLocalDb(parentMenuEntities: ArrayList<HomeParentMenuEntities>) =
        parentMenuDao.insertParentMenu(parentMenuEntities)

    suspend fun saveChildMenuLocalDb(childMenuEntities: ArrayList<HomeChildMenuEntities>) =
        childMenuDao.insertChildMenu(childMenuEntities)

    suspend fun getParentMenuLocalDb() = parentMenuDao.getParentMenu()
    suspend fun getChildMenuLocalDb() = childMenuDao.getChildMenu()
}