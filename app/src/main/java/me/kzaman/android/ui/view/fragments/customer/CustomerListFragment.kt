package me.kzaman.android.ui.view.fragments.customer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.adapter.CustomerListAdapter
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentCustomerListBinding
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.view.activities.CustomerActivity
import me.kzaman.android.ui.viewModel.CommonViewModel
import me.kzaman.android.utils.LoadingUtils
import me.kzaman.android.utils.handleNetworkError


@AndroidEntryPoint
class CustomerListFragment : BaseFragment<FragmentCustomerListBinding>(
    FragmentCustomerListBinding::inflate
) {
    private val viewModel by viewModels<CommonViewModel>()
    private lateinit var customerListAdapter: CustomerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingUtils = LoadingUtils(mContext)

        (activity as CustomerActivity).showToolbar(true) //display toolbar
        (activity as CustomerActivity).setToolbarTitle("Customers List")

        customerListAdapter = CustomerListAdapter(arrayListOf(), mContext)

        binding.rvCustomerList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = customerListAdapter
        }

        viewModel.getAllRemoteCustomer()

        viewModel.customers.observe(viewLifecycleOwner) {
            loadingUtils.isLoading(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {
                    customerListAdapter.setCustomers(it.value.customerList)
                }
                is Resource.Failure -> handleNetworkError(it, mActivity)
                else -> Log.d("unknownError", "Unknown Error")
            }
        }
    }
}