package me.kzaman.android.ui.view.fragments.orders


import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.adapter.OrderItemSelectionAdapter
import me.kzaman.android.data.model.ProductInfo
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.ProductListFragment
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.cartItemCounter

@AndroidEntryPoint
class ProductSelectionFragment : ProductListFragment() {

    companion object {
        val selectedProduct = ArrayList<ProductInfo>()
    }

    override fun initializeApp() {
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Select Product")
        val customerModel = OrdersActivity.customerModel
        binding.cvCustomerInfo.visibility = View.VISIBLE
        viewModel.displayCustomerInfo(customerModel)
        cartItemCounter.value = null
        productListAdapter = OrderItemSelectionAdapter(arrayListOf(), mContext)
        binding.productList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }

        cartItemCounter.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.tvCartItem.visibility = View.GONE
            } else {
                binding.tvCartItem.visibility = View.VISIBLE
            }
        }
    }

}