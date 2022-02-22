package me.kzaman.android.ui.view.fragments.orders


import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.ProductListFragment

@AndroidEntryPoint
class ProductSelectionFragment : ProductListFragment() {

    override fun init() {
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Select Product")
        val customerModel = OrdersActivity.customerModel
        binding.cvCustomerInfo.visibility = View.VISIBLE
        viewModel.displayCustomerInfo(customerModel)
    }

}