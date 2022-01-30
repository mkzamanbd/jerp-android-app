package com.example.mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.data.model.User
import com.example.mvvm.databinding.FragmentProfileBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.ProfileViewModel
import com.example.mvvm.utils.handleApiError
import com.example.mvvm.utils.logout
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)
        binding.profileInfo.visible(false)

        getUser()

        viewModel.profile.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.profileInfo.visible(true)
                    updateUI(it.value.user)
                }
                is Resource.Failure -> handleApiError(it){
                    getUser()
                }
            }
        })

        binding.userLogout.setOnClickListener {
            logout()
        }

    }

    private fun updateUI(user: User) {
        binding.tvName.text = user.name
        binding.tvEmail.text = user.email
        Log.d("user", user.toString())
    }

    private fun getUser(){
        viewModel.getUser()
    }
}

