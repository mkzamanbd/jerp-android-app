package me.kzaman.demo_app.ui.view.fragments.orders

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.demo_app.adapter.ChooseCustomerAdapter
import me.kzaman.demo_app.ui.view.activities.OrdersActivity
import me.kzaman.demo_app.ui.view.fragments.customer.CustomerListFragment


@AndroidEntryPoint
class CustomerSelectionFragment : CustomerListFragment() {

    companion object {
        var isCartItemsChanged = false
    }

    override fun initializeApp() {
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Choose A Customer")
        customerListAdapter = ChooseCustomerAdapter(arrayListOf(), mContext)
        binding.rvCustomerList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = customerListAdapter
        }
    }
}