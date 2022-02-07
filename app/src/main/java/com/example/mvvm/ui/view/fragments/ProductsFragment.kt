package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
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

        getProductList()

        viewModel.products.observe(viewLifecycleOwner) {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.d("usersList", it.value.productList.toString())
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

        val user = productListAdapter.products[position]
        val bundle = Bundle()
        bundle.putString("userId", user.id.toString())

        findNavController().navigate(R.id.action_userFragment_to_userDetailFragment, bundle)
    }
}