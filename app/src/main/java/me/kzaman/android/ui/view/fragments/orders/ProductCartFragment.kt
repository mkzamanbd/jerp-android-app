package me.kzaman.android.ui.view.fragments.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.R
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentProductCartBinding
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.viewModel.OrderViewModel
import me.kzaman.android.utils.LoadingUtils

@AndroidEntryPoint
class ProductCartFragment : BaseFragment<FragmentProductCartBinding>() {

    private lateinit var binding: FragmentProductCartBinding
    private val viewModel by viewModels<OrderViewModel>()
    override val layoutId: Int = R.layout.fragment_product_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
        binding.lifecycleOwner = viewLifecycleOwner
        binding.orderViewModel = viewModel
        initializeApp()
    }

    override fun initializeApp() {
        viewModel.displayCustomerInfo(OrdersActivity.customerModel)
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle(viewModel.mlCustomerName.value!!)
    }
}