package me.kzaman.demo_app.ui.view.fragments.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.demo_app.R
import me.kzaman.demo_app.adapter.CartConfirmationAdapter
import me.kzaman.demo_app.base.BaseFragment
import me.kzaman.demo_app.databinding.FragmentProductCartBinding
import me.kzaman.demo_app.ui.view.activities.OrdersActivity
import me.kzaman.demo_app.ui.view.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.demo_app.ui.viewModel.OrderViewModel
import me.kzaman.demo_app.utils.LoadingUtils
import me.kzaman.demo_app.utils.goToNextFragment

@AndroidEntryPoint
class PlacedNewOrderFragment : BaseFragment<FragmentProductCartBinding>() {

    private lateinit var binding: FragmentProductCartBinding
    private val viewModel by viewModels<OrderViewModel>()
    override val layoutId: Int = R.layout.fragment_product_cart

    private lateinit var cartConfirmationAdapter: CartConfirmationAdapter

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
        cartConfirmationAdapter = CartConfirmationAdapter(arrayListOf(), mContext)
        binding.rvProductCart.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = cartConfirmationAdapter
        }
        initializeApp()

        binding.tvUpdateCart.setOnClickListener {
            goToNextFragment(
                R.id.action_placedNewOrderFragment_to_productSelectionFragment,
                mRootView,
                null
            )
        }
        binding.tvHideExpand.setOnClickListener {
            if (binding.collapsableCustomerDetail.visibility == View.GONE) {
                binding.collapsableCustomerDetail.visibility = View.VISIBLE
                binding.tvHideExpand.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_baseline_keyboard_arrow_up_15,
                    0
                )
            } else {
                binding.collapsableCustomerDetail.visibility = View.GONE
                binding.tvHideExpand.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_baseline_keyboard_arrow_down_15,
                    0
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initializeApp() {
        viewModel.displayCustomerInfo(OrdersActivity.customerModel)
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        binding.tvHideExpand.visibility = View.VISIBLE
        binding.tvEmptyCart.visibility = View.GONE
        binding.buttonOrderNext.text = "Place Order"
        (activity as OrdersActivity).setToolbarTitle(viewModel.mlCustomerName.value!!)

        cartConfirmationAdapter.setProducts(selectedProduct)
    }
}