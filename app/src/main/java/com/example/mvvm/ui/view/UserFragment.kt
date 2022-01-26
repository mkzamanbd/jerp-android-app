package com.example.mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.databinding.FragmentUserBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.UserViewModel
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(
    FragmentUserBinding::inflate
) {
    private val viewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUsers()

        viewModel.users.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    Log.d("users", it.value.toString())
                }
                is Resource.Loading -> {
                    binding.progressBar.visible(true)
                }
            }
        })


    }
}