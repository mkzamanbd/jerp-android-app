package me.kzaman.android.repository

import me.kzaman.android.base.BaseRepository
import me.kzaman.android.database.dao.OrderDao
import me.kzaman.android.database.entities.CartItemsEntities
import me.kzaman.android.database.entities.ProductEntities
import me.kzaman.android.network.api.CommonApi
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: CommonApi,
    private val orderDao: OrderDao
) : BaseRepository(api) {

    suspend fun getRemoteProducts() = safeApiCall { api.getRemoteProducts() }

    suspend fun getLocalProducts() = orderDao.getLocalProducts()
    suspend fun saveLocalProducts(productEntities: ArrayList<ProductEntities>) = orderDao.insertProduct(productEntities)
    suspend fun saveCartProducts(cartItemsEntities: CartItemsEntities) = orderDao.insertCartProducts(cartItemsEntities)
}