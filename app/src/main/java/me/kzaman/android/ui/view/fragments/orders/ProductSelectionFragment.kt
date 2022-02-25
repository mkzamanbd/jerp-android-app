package me.kzaman.android.ui.view.fragments.orders


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.R
import me.kzaman.android.adapter.OrderItemSelectionAdapter
import me.kzaman.android.data.model.ProductInfo
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.ui.view.fragments.ProductListFragment
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.cartItemCounter
import me.kzaman.android.utils.LoadingUtils
import me.kzaman.android.utils.getProductFromCartJson
import me.kzaman.android.utils.goToNextFragment
import me.kzaman.android.utils.toastWarning

@AndroidEntryPoint
class ProductSelectionFragment : ProductListFragment() {

    companion object {
        val selectedProduct = ArrayList<ProductInfo>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
        cartItemCounter.value = null
    }

    override fun initializeApp() {
        (activity as OrdersActivity).showToolbar(true) //display toolbar
        (activity as OrdersActivity).setToolbarTitle("Choose Products")
        val customerModel = OrdersActivity.customerModel
        binding.cvCustomerInfo.visibility = View.VISIBLE
        viewModel.displayCustomerInfo(customerModel)
        productListAdapter = OrderItemSelectionAdapter(arrayListOf(), mContext)
        binding.productList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }

        cartItemCounter.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.tvCartItem.visibility = View.GONE
            } else {
                binding.tvCartItem.visibility = View.VISIBLE
            }
        }

        binding.rlCart.setOnClickListener {
            if (cartItemCounter.value.isNullOrEmpty()) {
                toastWarning(mActivity, "Please add product first and retry")
            } else {
                goToNextFragment(
                    R.id.action_productSelectionFragment_to_productCartFragment,
                    mRootView,
                    null
                )
            }
        }
        viewModel.getCustomerWiseCartItems(customerModel?.compositeKey!!)

        if (selectedProduct.size > 0) {
            viewModel.storeProductCartItem(selectedProduct)
            Toast.makeText(mContext, "Cart Item Saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun displayProductList(products: List<ProductInfo>) {
        val unSelectedProduct: List<ProductInfo> =
            viewModel.getOnlyUnselectedProducts(products as ArrayList<ProductInfo>, selectedProduct)
        viewModel.cartItems.observe(viewLifecycleOwner) {
            if (it != null) {
                selectedProduct.clear()
                selectedProduct.addAll(getProductFromCartJson(it.cartJson, products))
                cartItemCounter.value = "${selectedProduct.size}"
            }
            productListAdapter.setProducts(unSelectedProduct)
        }
    }

}