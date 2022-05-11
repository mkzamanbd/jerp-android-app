package me.kzaman.demo_app.ui.fragments.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.demo_app.R
import me.kzaman.demo_app.adapter.CartConfirmationAdapter
import me.kzaman.demo_app.base.BaseFragment
import me.kzaman.demo_app.data.model.ProductInfo
import me.kzaman.demo_app.data.model.SalesAreaModel
import me.kzaman.demo_app.databinding.FragmentProductCartBinding
import me.kzaman.demo_app.network.Resource
import me.kzaman.demo_app.ui.activities.OrdersActivity
import me.kzaman.demo_app.ui.fragments.orders.CustomerSelectionFragment.Companion.isCartItemsChanged
import me.kzaman.demo_app.ui.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.demo_app.ui.viewModel.OrderViewModel
import me.kzaman.demo_app.utils.LoadingUtils
import me.kzaman.demo_app.utils.goToNextFragment
import me.kzaman.demo_app.utils.handleNetworkError
import me.kzaman.demo_app.utils.toastSuccess
import me.kzaman.demo_app.utils.toastError
import me.kzaman.demo_app.utils.todayDate
import me.kzaman.demo_app.utils.Constants.Companion.RESPONSE_CREATED

@AndroidEntryPoint
class PlacedNewOrderFragment : BaseFragment<FragmentProductCartBinding>() {

    private lateinit var binding: FragmentProductCartBinding
    private val viewModel by viewModels<OrderViewModel>()
    override val layoutId: Int = R.layout.fragment_product_cart

    private lateinit var cartConfirmationAdapter: CartConfirmationAdapter
    private lateinit var territoryList: List<SalesAreaModel>

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
        getSalesAreaListObserver()
        binding.buttonOrderNext.setOnClickListener {
            placedNewOrder()
        }
    }

    private fun getSalesAreaListObserver() {
        viewModel.getAreaListByUser(OrdersActivity.customerModel?.customerId!!)

        viewModel.salesAreaList.observe(viewLifecycleOwner) {
            loadingUtils.isLoading(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.d("salesArea", it.value.salesAreas.toString())
                    val salesAreas = it.value.salesAreas
                    territoryList = salesAreas
                    binding.tvTerritoryCode.text = salesAreas[0].areaName
                }
                is Resource.Failure -> handleNetworkError(it, mActivity) {
                    getSalesAreaListObserver()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }
    }

    private fun placedNewOrder() {
        val gson = GsonBuilder().create()
        val productJson = gson.toJson(selectedProduct as List<ProductInfo>)
        Log.d("newOrder", productJson.toString())

        lifecycleScope.launch {
            viewModel.createOrder(
                "2",
                OrdersActivity.customerModel?.customerId!!,
                territoryList[0].areaId,
                "Test Order",
                productJson,
                viewModel.mlCustomerAddress.value!!,
                todayDate()
            )
        }

        viewModel.createOrderRequest.observe(viewLifecycleOwner) {
            loadingUtils.isLoading(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.d("response", it.value.toString())
                    val result = it.value
                    if (result.responseCode == RESPONSE_CREATED) {
                        toastSuccess(mActivity, "New order has been placed successfully")
                        isCartItemsChanged = true
                        selectedProduct.clear()
                        goToNextFragment(
                            R.id.action_placedNewOrderFragment_to_customerSelectionFragment,
                            mRootView,
                            null
                        )
                    }
                    else {
                        toastError(mActivity, result.message)
                    }
                }
                is Resource.Failure -> handleNetworkError(it, mActivity) {
                    placedNewOrder()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }
    }
}