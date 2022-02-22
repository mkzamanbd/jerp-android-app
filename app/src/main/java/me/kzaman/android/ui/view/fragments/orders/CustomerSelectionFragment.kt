package me.kzaman.android.ui.view.fragments.orders

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.adapter.ChooseCustomerAdapter
import me.kzaman.android.data.model.CustomerModel
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.customer.CustomerListFragment


@AndroidEntryPoint
class CustomerSelectionFragment : CustomerListFragment() {

    override fun init() {
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Choose Customer")
    }

    override fun displayCustomerList(customerModels: List<CustomerModel>) {
        customerListAdapter = ChooseCustomerAdapter(arrayListOf(), mActivity)
        binding.rvCustomerList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = customerListAdapter
        }
        customerListAdapter.setCustomers(customerModels)
    }

}