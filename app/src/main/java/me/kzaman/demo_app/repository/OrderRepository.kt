package me.kzaman.demo_app.repository

import me.kzaman.demo_app.base.BaseRepository
import me.kzaman.demo_app.database.dao.OrderDao
import me.kzaman.demo_app.database.entities.CartItemsEntities
import me.kzaman.demo_app.database.entities.ProductEntities
import me.kzaman.demo_app.network.api.CommonApi
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: CommonApi,
    private val orderDao: OrderDao,
) : BaseRepository(api) {

    suspend fun getRemoteProducts() = safeApiCall { api.getRemoteProducts() }

    suspend fun getLocalProducts() = orderDao.getLocalProducts()
    suspend fun saveLocalProducts(productEntities: ArrayList<ProductEntities>) {
        orderDao.insertProduct(productEntities)
    }
    suspend fun getCartItems(customerId: String) = orderDao.getCartItems(customerId)
    suspend fun saveCartProducts(cartItemsEntities: CartItemsEntities) {
        orderDao.insertCartProducts(cartItemsEntities)
    }
    suspend fun customerCartEmpty(customerId: String) = orderDao.customerCartEmpty(customerId)
}