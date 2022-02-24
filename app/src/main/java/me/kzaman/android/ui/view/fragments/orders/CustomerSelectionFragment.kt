package me.kzaman.android.ui.view.fragments.orders

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.android.adapter.ChooseCustomerAdapter
import me.kzaman.android.data.model.ProductInfo
import me.kzaman.android.database.entities.CartItemsEntities
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.customer.CustomerListFragment
import me.kzaman.android.ui.view.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.android.ui.viewModel.OrderViewModel
import org.json.JSONArray
import org.json.JSONObject


@AndroidEntryPoint
class CustomerSelectionFragment : CustomerListFragment() {

    companion object {
        var isCartItemsChanged = false
    }

    private val viewModel by viewModels<OrderViewModel>()

    override fun initializeApp() {
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Choose A Customer")
        customerListAdapter = ChooseCustomerAdapter(arrayListOf(), mActivity)
        binding.rvCustomerList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = customerListAdapter
        }

        if (selectedProduct.size > 0) {
            storeProductCartItem(selectedProduct)
            Toast.makeText(mContext, "Method Call", Toast.LENGTH_SHORT).show()
        }
    }

    private fun storeProductCartItem(products: List<ProductInfo>) {
        try {
            val productJson = JSONArray()
            products.forEach { product ->
                val jsonObject = JSONObject()
                jsonObject.put("id", product.productId)
                jsonObject.put("qty", product.quantity)
                productJson.put(jsonObject)
            }
            val cartItemsEntities = OrdersActivity.customerModel?.let {
                CartItemsEntities(
                    customerId = it.compositeKey,
                    productDetail = productJson.toString()
                )
            }
            lifecycleScope.launch {
                viewModel.saveCartProducts(cartItemsEntities!!)
                selectedProduct.clear()
                Log.d("storeJsonCart", cartItemsEntities.toString())
            }

        } catch (e: Exception) {
            Log.d("orderJsonException", e.toString())
        }

    }
}