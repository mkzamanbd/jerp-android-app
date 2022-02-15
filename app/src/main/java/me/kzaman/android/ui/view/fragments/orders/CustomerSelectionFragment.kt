package me.kzaman.android.ui.view.fragments.orders

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentCustomerSelectionBinding
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.utils.LoadingUtils


@AndroidEntryPoint
class CustomerSelectionFragment : BaseFragment<FragmentCustomerSelectionBinding>(
    FragmentCustomerSelectionBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingUtils = LoadingUtils(mContext)

        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Customers")
    }

}