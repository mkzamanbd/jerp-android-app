package me.kzaman.android.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import me.kzaman.android.adapter.ProductListAdapter
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentProductListBinding
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.view.activities.ProductActivity
import dagger.hilt.android.AndroidEntryPoint
import me.kzaman.android.ui.viewModel.ProductViewModel
import me.kzaman.android.utils.LoadingUtils
import me.kzaman.android.utils.hideSoftKeyboard
import me.kzaman.android.utils.toastWarning
import me.kzaman.android.utils.visible
import me.kzaman.android.utils.handleFragmentApiError

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding>(
    FragmentProductListBinding::inflate
) {

    private val viewModel by viewModels<ProductViewModel>()

    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productListAdapter = ProductListAdapter(arrayListOf(), mContext)
        loadingUtils = LoadingUtils(mContext)

        (activity as ProductActivity).showToolbar(true) //display toolbar
        (activity as ProductActivity).setToolbarTitle("Product List")

        val productListRecyclerView = binding.productList

        productListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }

        val etSearch = binding.etSearch
        val ivCancelSearch = binding.ivCancelSearch

        etSearch.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                ivCancelSearch.visible(false)
            } else {
                ivCancelSearch.visible(true)
            }
            productListAdapter.filter.filter(it)
        }

        binding.ivCancelSearch.setOnClickListener {
            etSearch.text = null
            hideSoftKeyboard(mContext, etSearch)
            ivCancelSearch.visible(false)
        }

        getProductList()

        viewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val response = it.value
                    if (response.responseCode == 200) {
                        productListAdapter.setProducts(it.value.productList)
                    } else {
                        mActivity.toastWarning("Product list list not found")
                    }
                }
                is Resource.Failure -> handleFragmentApiError(it) {
                    getProductList()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }
    }

    private fun getProductList() {
        viewModel.getRemoteProducts()
    }
}