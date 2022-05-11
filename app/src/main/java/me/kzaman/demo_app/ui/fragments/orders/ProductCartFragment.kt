package me.kzaman.demo_app.ui.fragments.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.demo_app.R
import me.kzaman.demo_app.adapter.ProductCartAdapter
import me.kzaman.demo_app.base.BaseFragment
import me.kzaman.demo_app.databinding.FragmentProductCartBinding
import me.kzaman.demo_app.ui.activities.OrdersActivity
import me.kzaman.demo_app.ui.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.demo_app.ui.viewModel.OrderViewModel
import me.kzaman.demo_app.ui.viewModel.OrderViewModel.Companion.mlDisplayGrandTotal
import me.kzaman.demo_app.utils.LoadingUtils
import me.kzaman.demo_app.utils.goToNextFragment

@AndroidEntryPoint
class ProductCartFragment : BaseFragment<FragmentProductCartBinding>() {

    private lateinit var binding: FragmentProductCartBinding
    private val viewModel by viewModels<OrderViewModel>()
    override val layoutId: Int = R.layout.fragment_product_cart

    private lateinit var productCartAdapter: ProductCartAdapter

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
        productCartAdapter = ProductCartAdapter(arrayListOf(), mContext)
        binding.rvProductCart.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = productCartAdapter
        }
        initializeApp()
        binding.tvHideExpand.visibility = View.GONE
        binding.collapsableCustomerDetail.visibility = View.GONE
        binding.tvUpdateCart.setOnClickListener {
            (activity as OrdersActivity).storeProductCartItem(selectedProduct)
            goToNextFragment(
                R.id.action_productCartFragment_to_productSelectionFragment,
                mRootView,
                null
            )
        }
        binding.tvEmptyCart.setOnClickListener {
            selectedProduct.clear()
            viewModel.customerCartEmpty(OrdersActivity.customerModel?.compositeKey!!)
            goToNextFragment(
                R.id.action_productCartFragment_to_productSelectionFragment,
                mRootView,
                null
            )
        }
        binding.buttonOrderNext.setOnClickListener {
            goToNextFragment(
                R.id.action_productCartFragment_to_placedNewOrderFragment,
                mRootView,
                null
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initializeApp() {
        viewModel.displayCustomerInfo(OrdersActivity.customerModel)
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle(viewModel.mlCustomerName.value!!)

        productCartAdapter.setProducts(selectedProduct)

        mlDisplayGrandTotal.observe(viewLifecycleOwner) {
            binding.tvTotalBill.visibility = View.VISIBLE
            binding.tvTotalBill.text = "Total: $it"
        }
    }
}