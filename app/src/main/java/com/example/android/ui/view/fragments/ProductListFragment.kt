package com.example.android.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.adapter.ProductListAdapter
import com.example.android.base.BaseFragment
import com.example.android.databinding.FragmentProductListBinding
import com.example.android.network.Resource
import com.example.android.ui.view.activities.ProductActivity
import com.example.android.ui.viewModel.CommonViewModel
import com.example.android.utils.LoadingUtils
import com.example.android.utils.hideSoftKeyboard
import com.example.android.utils.visible
import com.example.android.utils.toastWarning
import com.example.android.utils.handleFragmentApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding>(
    FragmentProductListBinding::inflate
) {

    private val viewModel by viewModels<CommonViewModel>()

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
            loadingUtils.isLoading(it is Resource.Loading)
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
        viewModel.getAllProducts()
    }
}