package me.kzaman.android.repository

import me.kzaman.android.base.BaseRepository
import me.kzaman.android.database.dao.ProductDao
import me.kzaman.android.database.entities.ProductEntities
import me.kzaman.android.network.api.CommonApi
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: CommonApi,
    private val productDao: ProductDao
) : BaseRepository(api) {

    suspend fun getRemoteProducts() = safeApiCall { api.getRemoteProducts() }

    suspend fun getLocalProducts() = productDao.getLocalProducts()
    suspend fun saveLocalProducts(productEntities: ArrayList<ProductEntities>) = productDao.insertProduct(productEntities)
}