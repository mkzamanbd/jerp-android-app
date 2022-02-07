package com.example.mvvm.ui.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.adapter.ProductListAdapter
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentProductBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.CommonViewModel
import com.example.mvvm.utils.handleApiError
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@Suppress("CAST_NEVER_SUCCEEDS")
@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductBinding>(
    FragmentProductBinding::inflate
), ProductListAdapter.OnItemClickListener {

    private val viewModel by viewModels<CommonViewModel>()

    private val productListAdapter = ProductListAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            ivCancelSearch.visible(false)
        }

        getProductList()

        viewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    productListAdapter.setProducts(it.value.productList)
                }
                is Resource.Failure -> handleApiError(it) {
                    getProductList()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }
    }

    private fun getProductList() {
        viewModel.getAllProducts()
    }

    override fun onItemClick(position: Int) {
        productListAdapter.notifyItemChanged(position)
        val product = productListAdapter.products[position]
        Toast.makeText(mContext, product.productId, Toast.LENGTH_SHORT).show()
    }
}