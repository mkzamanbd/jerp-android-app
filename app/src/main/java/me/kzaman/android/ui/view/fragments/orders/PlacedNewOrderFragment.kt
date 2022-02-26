package me.kzaman.android.ui.view.fragments.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.R
import me.kzaman.android.adapter.CartConfirmationAdapter
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentProductCartBinding
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.android.ui.viewModel.OrderViewModel
import me.kzaman.android.utils.LoadingUtils
import me.kzaman.android.utils.goToNextFragment

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
    }

    @SuppressLint("SetTextI18n")
    override fun initializeApp() {
        viewModel.displayCustomerInfo(OrdersActivity.customerModel)
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        binding.tvEmptyCart.visibility = View.GONE
        binding.buttonOrderNext.text = "Place Order"
        (activity as OrdersActivity).setToolbarTitle(viewModel.mlCustomerName.value!!)

        cartConfirmationAdapter.setProducts(selectedProduct)
    }
}