package com.example.mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentUserDetailBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.UserViewModel
import com.example.mvvm.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UserDetailFragment : BaseFragment<FragmentUserDetailBinding>(
    FragmentUserDetailBinding::inflate
) {
    private var userId: String? = null
    private val viewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getString("userId")
        if (userId != null) {
            Toast.makeText(requireContext(), userId, Toast.LENGTH_SHORT).show()
        }

        getUserDetail(userId.toString())

        viewModel.userDetail.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    Log.d("userDetail", it.toString())
                }
                is Resource.Failure -> handleApiError(it) {
                    Log.d("ErrorDetail", it.toString())
                    getUserDetail(userId.toString())
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        })
    }

    private fun getUserDetail(userId: String){
        viewModel.getUserDetail(userId)
    }
}