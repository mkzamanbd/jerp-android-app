package me.kzaman.android.ui.view.fragments.orders

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.adapter.ChooseCustomerAdapter
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.customer.CustomerListFragment
import me.kzaman.android.ui.view.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.android.ui.viewModel.OrderViewModel


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
            viewModel.storeProductCartItem(selectedProduct)
            Toast.makeText(mContext, "Cart Item Saved", Toast.LENGTH_SHORT).show()
        }
    }
}