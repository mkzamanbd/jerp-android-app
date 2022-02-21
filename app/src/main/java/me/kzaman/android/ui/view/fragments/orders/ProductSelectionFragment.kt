package me.kzaman.android.ui.view.fragments.orders


import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.ProductListFragment
import me.kzaman.android.ui.viewModel.OrderViewModel

@AndroidEntryPoint
class ProductSelectionFragment : ProductListFragment() {

    private val viewModel by viewModels<OrderViewModel>()

    override fun init() {
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Select Product")
        val customerModel = OrdersActivity.customerModel
        binding.cvCustomerInfo.visibility = View.VISIBLE


        viewModel.displayCustomerInfo(customerModel)


    }

}