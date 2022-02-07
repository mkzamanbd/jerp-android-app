package com.example.mvvm.ui.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.databinding.FragmentProfileBinding
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.ProfileViewModel
import com.example.mvvm.utils.handleApiError
import com.example.mvvm.utils.logout
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    @Inject
    lateinit var prefManager: SharedPreferenceManager
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visible(false)

        getUser()
        updateUI()

        viewModel.profile.observe(viewLifecycleOwner){
            binding.progressBar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    binding.tvName.text = it.value.data.toString()
                }
                is Resource.Failure -> handleApiError(it){
                    getUser()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }

        binding.userLogout.setOnClickListener {
            logout()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        binding.tvEmail.text = "Local=> Name ${prefManager.getUserFullName()}, Email: ${prefManager.getUserRoleName()}"
    }

    private fun getUser() {
        viewModel.getUser()
    }
}

