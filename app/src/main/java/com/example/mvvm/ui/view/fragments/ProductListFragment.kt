package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.adapter.ProductListAdapter
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentProductListBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.view.activities.ProductActivity
import com.example.mvvm.ui.viewModel.CommonViewModel
import com.example.mvvm.utils.handleFragmentApiError
import com.example.mvvm.utils.hideSoftKeyboard
import com.example.mvvm.utils.visible
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
                    productListAdapter.setProducts(it.value.productList)
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