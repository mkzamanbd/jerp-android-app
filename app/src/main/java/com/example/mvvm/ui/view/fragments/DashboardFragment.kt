package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentDashboardBinding
import com.example.mvvm.network.Resource
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

        getMobileMenu()

        viewModel.mobileMenu.observe(viewLifecycleOwner){
            binding.progressBar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success ->{
                    Log.d("mobileMenu", it.value.data.toString())
                    binding.tvMobileMenu.text = it.value.data.toString()
                }
                is Resource.Failure -> handleApiError(it){
                    getMobileMenu()
                }
                else -> Log.d("error", "Unknown Error")
            }
        }
    }

    private fun getMobileMenu(){
        viewModel.getMobileMenu()
    }
}