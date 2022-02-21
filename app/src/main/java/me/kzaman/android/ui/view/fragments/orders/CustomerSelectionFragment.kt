package me.kzaman.android.ui.view.fragments.orders

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.adapter.ChooseCustomerAdapter
import me.kzaman.android.data.model.CustomerModel
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.customer.CustomerListFragment


@AndroidEntryPoint
class CustomerSelectionFragment : CustomerListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun init() {
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Choose Customers")
    }

    override fun displayCustomerList(customerModels: List<CustomerModel>){
        customerListAdapter = ChooseCustomerAdapter(arrayListOf(), mContext)
        rvCustomerList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = customerListAdapter
        }
        customerListAdapter.setCustomers(customerModels)
    }

}