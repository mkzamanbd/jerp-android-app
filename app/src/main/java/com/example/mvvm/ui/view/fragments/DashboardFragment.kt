package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mvvm.R
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentDashboardBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.view.activities.DashboardActivity
import com.example.mvvm.ui.viewModel.CommonViewModel
import com.example.mvvm.utils.handleApiError
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
) {

    private val viewModel by viewModels<CommonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).hideToolbar() //display toolbar

        binding.tvProductList.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_productFragment)
        }

        getMobileMenu()

        viewModel.mobileMenu.observe(viewLifecycleOwner) {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.d("mobileMenu", it.value.data.toString())
                    binding.tvMobileMenu.text = it.value.data.toString()
                }
                is Resource.Failure -> handleApiError(it) {
                    getMobileMenu()
                }
                else -> Log.d("error", "Unknown Error")
            }
        }
    }

    private fun getMobileMenu() {
        viewModel.getMobileMenu()
    }
}