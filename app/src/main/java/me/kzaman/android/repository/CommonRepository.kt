package me.kzaman.android.repository

import me.kzaman.android.base.BaseRepository
import me.kzaman.android.database.dao.CustomerDao
import me.kzaman.android.database.dao.MenuDao
import me.kzaman.android.database.entities.CustomerEntities
import me.kzaman.android.database.entities.SubMenuEntities
import me.kzaman.android.database.entities.MenuEntities
import me.kzaman.android.network.api.CommonApi
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val api: CommonApi,
    private val menuDao: MenuDao,
    private val customerDao: CustomerDao,
) : BaseRepository(api) {

    suspend fun getMobileMenu() = safeApiCall { api.getMobileMenu() }

    suspend fun saveParentMenuLocalDb(parentMenuEntities: ArrayList<MenuEntities>) =
        menuDao.insertParentMenu(parentMenuEntities)

    suspend fun saveSubMenuLocalDb(childMenuEntities: ArrayList<SubMenuEntities>) =
        menuDao.insertSubMenu(childMenuEntities)

    suspend fun getParentMenuLocalDb() = menuDao.getParentMenu()
    suspend fun getSubMenuLocalDb() = menuDao.getSubMenu()

    // get customer
    suspend fun getAllRemoteCustomer() = safeApiCall { api.getAllCustomers() }
    suspend fun saveCustomersLocalDb(customerEntities: ArrayList<CustomerEntities>) =
        customerDao.insertCustomers(customerEntities)

    suspend fun getAllCustomersLocalDb() = customerDao.getLocalCustomers()
}