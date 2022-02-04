package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentUserDetailBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.UserViewModel
import com.example.mvvm.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : BaseFragment<FragmentUserDetailBinding>(
    FragmentUserDetailBinding::inflate
) {
    private var userId: String? = null
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getString("userId")

        if (userId != null) {
            getUserDetail(userId.toString())
        }

        viewModel.userDetail.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.userDetail.text = it.value.user.toString()
                }
                is Resource.Failure -> handleApiError(it) {
                    getUserDetail(userId.toString())
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        })
    }

    private fun getUserDetail(userId: String) {
        viewModel.getUserDetail(userId)
    }
}