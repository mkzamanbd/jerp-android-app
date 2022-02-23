package me.kzaman.android.ui.view.fragments.orders

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.adapter.ChooseCustomerAdapter
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.customer.CustomerListFragment


@AndroidEntryPoint
class CustomerSelectionFragment : CustomerListFragment() {

    companion object {
        var isCartItemsChanged = false
    }

    override fun initializeApp() {
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Choose A Customer")
        customerListAdapter = ChooseCustomerAdapter(arrayListOf(), mActivity)
        binding.rvCustomerList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = customerListAdapter
        }
    }
}